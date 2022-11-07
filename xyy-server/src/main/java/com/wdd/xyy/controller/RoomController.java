package com.wdd.xyy.controller;

import com.wdd.xyy.mapper.AdminRoomMapper;
import com.wdd.xyy.mapper.RoomMapper;
import com.wdd.xyy.pojo.AdminRoom;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.pojo.Room;
import com.wdd.xyy.service.AdminRoomService;
import com.wdd.xyy.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.annotation.*;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 预定座位信息（学生端口）
 * @author: wdd
 * @date: 2022/10/29 22:12
 */
@RestController
@RequestMapping("/reserve/classroom")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private AdminRoomMapper adminRoomMapper;

    @Autowired
    private AdminRoomService adminRoomService;

    /**
     * 查询所有的自习室
     * @return
     */
    @GetMapping("/")
    public List<Room> getRooms(){
       return roomService.getRooms();
    }

    /**
     * 预定自习室座位
     * @param adminRoom
     * @return
     */
    @PutMapping("/")
    public RespBean bookSeat(@RequestBody AdminRoom adminRoom){
        // 拿到adminId 和roomId
        Integer adminId = adminRoom.getAdminId();
        Integer roomId = adminRoom.getRoomId();
        // 用adminId查询出来联表t_admin_room 是否存在roomId
        Integer byRoomId = adminRoomService.getAdminRoom(adminId);
        // 如果存在 返回 重新选择
        if (byRoomId!=0){
            return RespBean.error("您已经预定了自习室，不能重复预定");
        }
        // 如果不存在，查询出来自习室 插入disabled 被预定的标识 再插入联表
        Room room = roomMapper.selectById(roomId);
        room.setDisabled(true);
        // 设置时间格式
        room.setDateTime(LocalDateTime.now());
        roomMapper.updateById(room);
        adminRoomMapper.insert(adminRoom);
        return RespBean.success("预定成功,快去学习！");
    }

    /**
     * 获得自习室的教师信息
     * @return
     */
    @PostMapping("/")
    public List<Object>  getRoomTeacher(){
        return roomService.getRoomTeacher();
    }





}
