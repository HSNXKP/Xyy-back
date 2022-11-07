package com.wdd.xyy.controller;

import com.wdd.xyy.pojo.Admin;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.pojo.RespPageBean;
import com.wdd.xyy.pojo.Room;
import com.wdd.xyy.service.AdminRoomService;
import com.wdd.xyy.service.AdminService;
import com.wdd.xyy.service.RoomService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端
 * @author: wdd
 * @date: 2022/11/3 14:42
 */
@RequestMapping("/system/basic")
@RestController
public class AdminConfigController {

    @Autowired
    public AdminService adminService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRoomService adminRoomService;

    /**
     * 查询全部的用户
     * @param currentPage
     * @param size
     * @param admin
     * @return
     */
    @GetMapping("/")
    public RespPageBean getAllAdmins(@RequestParam(defaultValue = "1") Integer currentPage,
                                     @RequestParam(defaultValue = "5") Integer size,
                                     Admin admin
                                    ){
        return adminService.getAllAdmins(currentPage,size,admin);
    }

    /**
     * 学生选座信息
     * @param adminId
     * @return
     */
    @GetMapping("/dataInfo")
    public List<Room> getDataInfoStudent(@RequestParam("adminId") Integer adminId){
        return roomService.getDataInfoStudent(adminId);
    }

    /**
     * 老师管理教室信息
     * @param admin
     * @return
     */
    @PostMapping("/dataInfo")
    public List<Room> getDataInfoTeacher(@RequestBody() Admin admin){
        Integer adminId = admin.getId();
        return roomService.getDataInfoTeacher(adminId);
    }

    /**
     * 管理更新用户信息
     * @param admin
     * @return
     */
    @PostMapping("/")
    public RespBean updateAdminInfo(@RequestBody Admin admin){
        String password = admin.getPassword();
        String encode = passwordEncoder.encode(password);
        admin.setPassword(encode);
        if (adminService.updateById(admin)){
           return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @GetMapping("/delete")
    public RespBean deleteAdminInfo(@RequestParam("id") Integer id){
        // 判断当前的用户是否绑定了教室 或者学生绑定了自习室座位
        Integer adminRoom = adminRoomService.getAdminRoom(id);
        if (adminRoom!=0){
            return RespBean.error("删除失败,该用户有预定的自习室或教室管理未解除");
        }
        if (adminService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }






}
