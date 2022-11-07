package com.wdd.xyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.xyy.exception.AnonyException;
import com.wdd.xyy.mapper.MenuMapper;
import com.wdd.xyy.pojo.Admin;
import com.wdd.xyy.pojo.Menu;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * 菜单服务实现类
 *
 * @author: wdd
 * @date: 2022/10/23 17:34
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 通过用户id查询菜单列表
     *
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken){
           throw new AnonyException("用户未登录");
        }

        // 拿到用户ID
        Integer adminId = ((Admin) authentication.getPrincipal()).getId();
            // 参数 :角色Id
            return menuMapper.getMenusByAdminId(adminId);

    }




    /**
     * 获取全部的菜单
     *
     * @return
     */
    @Override
    public List<Menu> getMenusWithRoles() {
        return menuMapper.getMenusWithRoles();
    }


}
