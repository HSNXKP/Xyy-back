package com.wdd.xyy.controller;

import com.alibaba.fastjson.JSON;
import com.wdd.xyy.mapper.MenuMapper;
import com.wdd.xyy.pojo.Admin;
import com.wdd.xyy.pojo.AdminLoginParam;
import com.wdd.xyy.pojo.Menu;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.service.AdminService;
import com.wdd.xyy.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录接口
 * @author: wdd
 * @date: 2022/10/22 20:59
 */
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MenuService menuService;

    /**
     * 登录
     * @param adminLoginParam
     * @param request
     * @return
     */
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(),adminLoginParam.getCode(), request);
    }


    /**
     * 返回前端用户信息
     * @param principal
     * @return
     */
    @GetMapping("/admin/info")
    public Admin loginInfo(Principal principal){
        if (principal == null){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }

    /**
     * 获得角色菜单
     * @return
     */
    @GetMapping("/menu")
    public List<Menu> getMenusByAdmin(){
        // 刷新页面的时候会重新返回空的
        return menuService.getMenusByAdminId();
    }





}




