package com.wdd.xyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdd.xyy.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id查询菜单列表
     * @param roleId
     * @return
     */
    List<Menu> getMenusByAdminId(Integer roleId);


    /**
     * 获得全部的菜单
     * @return
     */
    List<Menu> getMenusWithRoles();

}
