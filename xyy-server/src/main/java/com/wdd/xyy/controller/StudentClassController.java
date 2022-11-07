package com.wdd.xyy.controller;

import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.pojo.Room;
import com.wdd.xyy.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预约的自习室信息（学生端口）
 * @author: wdd
 * @date: 2022/11/5 10:55
 */
@RestController
@RequestMapping("/book/classroom")
public class StudentClassController {

    @Autowired
    private RoomService roomService;

    /**
     * 获得自己预约的自习室信息
     * @param adminId
     * @return
     */
    @GetMapping("/dataInfo")
    public Room getDataInfo(@RequestParam("adminId") Integer adminId){
        return roomService.getDataInfo(adminId);
    }

    /**
     * 取消预约的自习室信息
     * @param id
     * @return
     */
    @GetMapping("/cancel")
    public RespBean deleteDataInfo(@RequestParam("id") Integer id){
        System.out.println(id);
        return roomService.deleteDataInfo(id);

    }
}
