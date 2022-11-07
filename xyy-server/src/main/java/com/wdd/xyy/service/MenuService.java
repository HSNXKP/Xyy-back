package com.wdd.xyy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wdd.xyy.pojo.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {


    /**
     * 通过用户id查询菜单列表
     * @return
     */
    List<Menu> getMenusByAdminId();

    /**
     * 查询全部的菜单
     * @return
     */
    List<Menu> getMenusWithRoles();



}
