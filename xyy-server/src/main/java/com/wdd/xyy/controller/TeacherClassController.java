package com.wdd.xyy.controller;

import com.wdd.xyy.pojo.AdminRoom;
import com.wdd.xyy.pojo.Room;
import com.wdd.xyy.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 老师管理的自习室信息
 * @author: wdd
 * @date: 2022/11/1 22:28
 */
@RestController
@RequestMapping("/classroom/config")
public class TeacherClassController {

    @Autowired
    private RoomService roomService;

    /**
     * 获得当前老师管理的自习室信息
     * @param adminId
     * @return
     */
    @GetMapping("/")
    public List<Object> getTeacherConfig(@RequestParam("adminId") Integer adminId){
        return roomService.getTeacherConfig(adminId);
    }

    /**
     * 获得自习室座位中的学生信息
     * @param adminId
     * @param roomId
     * @return
     */
    @GetMapping("/student")
    public List<Room> getStudentData(@RequestParam("adminId") Integer adminId ,@RequestParam("roomId") Integer roomId){
        return roomService.getStudentData(adminId,roomId);

    }

}
