package com.wdd.xyy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.xyy.mapper.RoomMapper;
import com.wdd.xyy.pojo.*;
import com.wdd.xyy.service.AdminRoomService;
import com.wdd.xyy.service.AdminService;
import com.wdd.xyy.service.RoomService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 教室信息管理
 * @author: wdd
 * @date: 2022/11/6 10:12
 */
@RestController
@RequestMapping("/system/config")
public class ClassroomConfigController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRoomService adminRoomService;

    /**
     * 查看所有的自习室信息
     * @return
     */
    @PostMapping("/")
    private List<Room> getAllRooms(){
        return roomService.getRooms();
    }

    /**
     * 通过房间id查询admin信息
     * @param roomId
     * @return
     */
    @GetMapping("/")
    private List<Room> getAdminWithRoom(@RequestParam("id") Integer roomId){
        return roomService.getTeacherByRoomId(roomId);
    }

    /**
     * 更新自习室名称
     * @param room
     * @return
     */
    @PostMapping("/update")
    private RespBean updateClassRoomData(@RequestBody Room room){
        if (roomService.updateById(room)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    /**
     * 增加座位
     * @param room
     * @return
     */
    @PostMapping("/addSeat")
    private RespBean addRoomSeat(@RequestBody Room room){
        // 拿到共享的表单信息
        Integer id = room.getId();
        String name = room.getName();
        Room seatRoom = new Room();
        seatRoom.setParentId(id);
        seatRoom.setName(name);
        seatRoom.setDisabled(false);
        if (roomService.save(seatRoom)) {
            return RespBean.success("增加成功");
        }
        return RespBean.error("增加失败");
    }

    /**
     * 增加自习室
     * @param room
     * @return
     */
    @PostMapping("/addRoom")
    private RespBean addRoom(@RequestBody Room room){
        Integer teacherId = room.getId();
        // 拿到共享的表单信息
        String name = room.getName();
        if (name == null || name.equals("")) {
            return RespBean.error("自习室名称不能为空");
        }else if (teacherId == null) {
            return RespBean.error("教师id不能为空");
        }else if (roomService.getOne(new QueryWrapper<Room>().eq("name", name)) != null) {
            return RespBean.error("自习室名称已存在");
        }
        // 将拿到的信息 新建一个Room
        Room roomRoom = new Room();
        roomRoom.setName(name);
        roomRoom.setParentId(0);
        roomRoom.setDisabled(true);
        roomService.save(roomRoom);
        // 拿出来刚增加的房间
        Room addRoom = roomService.getOne(new QueryWrapper<Room>().eq("name", name));
        // 加入一个子元素
        Integer addRoomId = addRoom.getId();
        Room roomSeat = new Room();
        roomSeat.setParentId(addRoomId);
        roomSeat.setName("默认座位");
        // 设置座位未预约
        roomSeat.setDisabled(false);
        // 将addRoomId 和teacherId绑定到一起
        AdminRoom adminRoom = new AdminRoom();
        adminRoom.setRoomId(addRoomId);
        adminRoom.setAdminId(teacherId);
        adminRoomService.save(adminRoom);
        // 再加一个子元素 座位就ok
        if (roomService.save(roomSeat)) {
            return RespBean.success("添加成功,默认添加一个座位");
        }
        return RespBean.error("添加失败");

    }

    /**
     * 删除房间和座位
     * @param roomId
     * @return
     */
    @GetMapping("/delete")
    private RespBean deleteRoom(@RequestParam("id") Integer roomId){
        return roomService.deleteRoom(roomId);
    }


    /**
     * 获得所有老师
     * @return
     */
    @GetMapping("/teacher")
    private List<Admin> getAllTeachers(){
        return adminService.getAllTeachers();
    }

    /**
     * 更新教师管理的自习室
     * @return
     */
    @PostMapping("/updateAdminRoom")
    public RespBean updateTeacherRoom(@RequestBody AdminRoom adminRoom){
        // 拿到教室id 把教室id
        Integer roomId = adminRoom.getRoomId();
        AdminRoom adminRoomSelect = adminRoomService.getOne(new QueryWrapper<AdminRoom>().eq("room_id", roomId));
        // 将原先的数据删除
        adminRoomService.removeById(adminRoomSelect.getId());
        // 重新计算时间 默认一个老师的值班时间为8个小时
        Room room = roomService.getOne(new QueryWrapper<Room>().eq("id", roomId));
        room.setDateTime(LocalDateTime.now());
        room.setExpireTime(LocalDateTime.now().plusHours(8));
        roomService.updateById(room);
        if (adminRoomService.save(adminRoom)) {
            return RespBean.success("更改教师成功");
        }
        return RespBean.error("更新教师失败");
    }

}
