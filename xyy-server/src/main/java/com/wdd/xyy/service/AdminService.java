package com.wdd.xyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdd.xyy.pojo.Admin;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.pojo.RespPageBean;
import com.wdd.xyy.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface AdminService extends IService<Admin> {

    /**
     * 注册账号
     * @param admin
     */
    RespBean registerAdmin(Admin admin);


    /**
     * 通过用户名获得用户信息
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * 登录
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);


/**
     * 通过用户id查询菜单列表
     * @return
     */
    List<Role> getRoles(Integer id);

    /**
     * 获得所有人的信息
     * @return
     */
    RespPageBean getAllAdmins(Integer currentPage, Integer size, Admin admin);


    /**
     * 获得所有老师
     * @return
     */
    List<Admin> getAllTeachers();

}

