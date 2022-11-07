package com.wdd.xyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.xyy.exception.AdminFullException;
import com.wdd.xyy.mapper.AdminMapper;
import com.wdd.xyy.mapper.AdminRoomMapper;
import com.wdd.xyy.mapper.RoomMapper;
import com.wdd.xyy.pojo.*;
import com.wdd.xyy.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: wdd
 * @date: 2022/10/29 22:09
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private AdminRoomMapper adminRoomMapper;

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 查询获取所有的自习室及所有座位预定情况
     * @return
     */
    @Override
    public List<Room> getRooms() {
        return roomMapper.getRooms();
    }

    /**
     * 查询老师管理的教室
     * @return
     */
    @Override
    public List<Object>  getRoomTeacher() {
        return roomMapper.getRoomTeacher();
    }

    /**
     * 获得当前预约人的信息
     * @return
     */
    @Override
    public Room getDataInfo(Integer adminId) {
        return roomMapper.getDataInfo(adminId);
    }


    /**
     * 删除座位信息
     * @param id
     * @return
     */
    @Override
    public RespBean deleteDataInfo(Integer id) {
        Room room = roomMapper.selectById(id);
        room.setDateTime(null);
        room.setDisabled(false);
        roomMapper.updateById(room);
        AdminRoom adminRoom = adminRoomMapper.selectAdminWithRoom(id);
        Integer adminRoomId = adminRoom.getId();
        adminRoomMapper.deleteById(adminRoomId);
        return RespBean.success("取消预定成功");
    }

    /**
     * 查询老师管理的自习室信息
     * @param adminId
     * @return
     */
    @Override
    public List<Object> getTeacherConfig(Integer adminId) {
        // 管理员和老师都可以查询 所以要判断当前角色是否为老师还是管理
        List<Role> roles = adminMapper.getRoles(adminId);

        if (roles.get(0).getName().equals("teacher")){
            return roomMapper.getTeacherConfig(adminId);
        }
        // 管理员登录的话 直接查询所有的学生自习室信息
        return roomMapper.getTeacherConfig(null);
    }

    /**
     * 返回学生信息和座位信息
     * @param adminId
     * roomId
     * @return
     */
    @Override
    public List<Room> getStudentData( Integer adminId ,Integer roomId) {
        return roomMapper.getStudentData(roomId,adminId);

    }

    /**
     * 返回学生选座信息（管理端）
     * @param adminId
     * @return
     */
    @Override
    public List<Room> getDataInfoStudent(Integer adminId) {
        return roomMapper.getDataInfoStudent(adminId);
    }

    /**
     * 老师管理教室信息（管理端）
     * @param adminId
     * @return
     */
    @Override
    public List<Room> getDataInfoTeacher(Integer adminId) {
        return roomMapper.getDataInfoTeacher(adminId);
    }

    /**
     * 通过房间id查询admin信息
     * @return
     */
    @Override
    public List<Room> getTeacherByRoomId(Integer roomId) {
        // 先判断传入的roomId 是否有对应的adminId
        Integer adminIsFull = roomMapper.getAdminIsFull(roomId);
        if (adminIsFull == 0){
            // 抛异常
            throw  new AdminFullException("该自习室或座位未绑定用户信息,请绑定再查看");
        }
        return roomMapper.getTeacherByRoomId(roomId);
    }

    /**
     * 通过父元素查询是否有关联
     * @param roomId
     * @return
     */
    @Override
    public RespBean deleteRoom(Integer roomId) {
        Room roomSelect = roomMapper.selectOne(new QueryWrapper<Room>().eq("id", roomId));
        Integer parentId = roomSelect.getParentId();
        if (parentId == 0){
            // 先判断父id有没有绑定的关系
            Integer adminIsFull = roomMapper.getAdminIsFull(roomId);
            if (adminIsFull !=null){
                return RespBean.error("该自习室绑定的教师未解除");
            }
            // 先通过父id查询到关联的子id
            List<Room> childrenId = roomMapper.getChildrenId(roomId);
            for (Room room : childrenId) {
                Integer adminIsFull1 = roomMapper.getAdminIsFull(room.getId());
                if (adminIsFull1 !=null){
                    return RespBean.error("该自习室中还有自习的学生,无法删除");
                }
            }
            roomMapper.deleteById(roomId);
            // 再将子id 拿出来进行删除
            List<Room> childrenId1 = roomMapper.getChildrenId(roomId);
            for (Room room : childrenId1) {
                roomMapper.deleteById(room.getId());
            }
            return RespBean.success("删除成功");
        }
        // 先判断是否有绑定的关系
        Integer adminIsFull = roomMapper.getAdminIsFull(roomId);
        if (adminIsFull !=0){
            return RespBean.error("该座位还有同学预约");
        }
        Room childrenRoom = roomMapper.selectOne(new QueryWrapper<Room>().eq("id", roomId));
        // 拿到父元素的id
        Integer childrenParentId = childrenRoom.getParentId();
        List<Room> childrenId = roomMapper.getChildrenId(childrenParentId);
        // 定义一个计算
        int count = 0;
        for (Room room : childrenId) {
            System.out.println(room.getId());
            count++;
        }
        if (count == 1){
            // 判断是否有关联
            Integer adminRoom = adminRoomMapper.getAdminRoom(roomId);
            if (adminRoom ==0){
                // 说明现在的自习室就剩下一个座位 并且没有人预约 可以进行删除
                roomMapper.deleteById(childrenParentId);
                roomMapper.deleteById(roomId);
                // 删除自习室绑定的老师信息
                adminRoomMapper.delete(new QueryWrapper<AdminRoom>().eq("room_id", childrenParentId));
                // 删除自习室
                roomMapper.deleteById(childrenParentId);
                return RespBean.success("删除成功");
            }
            return RespBean.error("该座位还有预约的同学,删除失败");

        }
        Integer result= roomMapper.deleteById(roomId);
        if (result!=null){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");

    }



}
