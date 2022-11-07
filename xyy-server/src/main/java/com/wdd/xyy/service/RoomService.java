package com.wdd.xyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdd.xyy.pojo.Admin;
import com.wdd.xyy.pojo.AdminRoom;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.pojo.Room;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RoomService extends IService<Room> {

    /**
     * 查询获取所有的自习室及所有座位预定情况
     * @return
     */
    List<Room> getRooms();


    /**
     *查询老师管理的教室
     * @return
     */
    List<Object>  getRoomTeacher();

    /**
     * 获得当前预约人的信息
     * @return
     */
    Room getDataInfo(Integer adminId);

    /**
     * 删除座位信息
     * @param id
     * @return
     */
    RespBean deleteDataInfo(Integer id);

    /**
     * 查询老师管理的自习室信息
     * @param adminId
     * @return
     */
    List<Object> getTeacherConfig(Integer adminId);

    /**
     * 返回座位信息和学生信息
     * @param adminId
     * @param roomId
     * @return
     */
    List<Room> getStudentData(Integer adminId ,Integer roomId);

    /**
     * 返回学生的选座信息（管理端）
     * @param adminId
     * @return
     */
    List<Room> getDataInfoStudent(Integer adminId);

    /**
     * 老师管理教室信息(管理端)
     * @param adminId
     * @return
     */
    List<Room> getDataInfoTeacher(Integer adminId);

    /**
     * 通过房间id查询admin信息
     * @return
     */
    List<Room> getTeacherByRoomId(Integer roomId);

    /**
     * 查询是否有关联
     * @param roomId
     * @return
     */
    RespBean deleteRoom(Integer roomId);
}
