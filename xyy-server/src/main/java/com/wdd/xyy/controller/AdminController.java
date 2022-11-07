package com.wdd.xyy.controller;

import com.wdd.xyy.pojo.Admin;
import com.wdd.xyy.pojo.Password;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 用户信息（所有人都可访问的端口）
 * @author: wdd
 * @date: 2022/10/21 23:54
 */
@RestController
@RequestMapping("/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 注册账号
     * @param admin
     * @return
     */
    @PostMapping("/register")
    public RespBean registerAdmin(@RequestBody Admin admin){
         return adminService.registerAdmin(admin);
    }

    /**
     * 更新个人信息
     * @param admin
     * @param authentication
     * @return
     */
    @PutMapping("/update")
    public RespBean updateInfo(@RequestBody Admin admin, Authentication authentication){
        if (adminService.updateById(admin)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin,null,authentication.getAuthorities()));
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    /**
     * 修改用户密码
     * @param password
     * @return
     */
    @PostMapping("/")
    public RespBean updatePassword(@RequestBody Password password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String oldPass = password.getOldPass();
        // 要更新的密码
        String pass = password.getPass();
        Integer id = password.getId();
        Admin admin = adminService.getById(id);
        // 使用Security自带的密码校验器校验
        if (encoder.matches(oldPass,admin.getPassword())){
            admin.setPassword(passwordEncoder.encode(pass));
            if (adminService.updateById(admin)) {
                return RespBean.success("修改成功");
            }
            return RespBean.error("修改失败");
        }
        return RespBean.error("您输入的旧密码有误，请重新输入");

    }



}
