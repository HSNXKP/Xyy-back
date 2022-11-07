package com.wdd.xyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdd.xyy.pojo.Admin;
import com.wdd.xyy.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 通过用户名获得用户信息
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * 通过用户id查询角色列表
     * @param id
     * @return
     */
    List<Role> getRoles(Integer id);

    /**
     * 查询username是否独一
     * @param username
     */
    String getUserNameByUserName(String username);

    /**
     * 查询所有人的信息
     * @return
     */
    IPage<Admin> getAllAdmins(Page<Admin> page, @Param("admin") Admin admin);


    /**
     * 获得所有老师
     * @return
     */
    List<Admin> getAllTeachers();

}

