package com.wdd.xyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.wdd.xyy.pojo.Admin;
import com.wdd.xyy.pojo.AdminRoom;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.pojo.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface RoomMapper extends BaseMapper<Room> {

    /**
     * 查询获取所有的自习室及所有座位预定情况
     * @return
     */
    List<Room> getRooms();


    /**
     * 查询老师管理的教室
     * @return
     */
    List<Object> getRoomTeacher();

    /**
     * 获得当前预约人的信息
     * @return
     */
    Room getDataInfo(Integer adminId);


    /**
     * 查询老师管理的自习室信息
     * @param adminId
     * @return
     */
    List<Object> getTeacherConfig(@Param("adminId") Integer adminId);

    /**
     * 返回学生信息和座位信息
     * @param roomId
     * @param adminId
     * @return
     */
    List<Room> getStudentData(@Param("roomId") Integer roomId, @Param("adminId") Integer adminId);

    /**
     * 返回学生座位信息（管理端）
     * @param adminId
     * @return
     */
    List<Room> getDataInfoStudent(Integer adminId);

    /**
     * 老师管理教室信息（管理端）
     * @param adminId
     * @return
     */
    List<Room> getDataInfoTeacher(Integer adminId);

    /**
     * 通过房间id查询admin信息
     * @param roomId
     * @return
     */
    List<Room> getTeacherByRoomId(Integer roomId);

    /**
     * 判断传入的roomId 查询adminId是否为空
     * @param roomId
     */
    Integer getAdminIsFull(Integer roomId);

    /**
     * 通过父元素查询子id
     * @param roomId
     */
    List<Room> getChildrenId(Integer roomId);
}
