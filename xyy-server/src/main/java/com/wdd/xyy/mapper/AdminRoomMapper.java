package com.wdd.xyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdd.xyy.pojo.AdminRoom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminRoomMapper extends BaseMapper<AdminRoom> {


    /**
     * getAdminRoom
     * @param adminId
     */
    Integer getAdminRoom(Integer adminId);

    /**
     * 查询admin with room
     * @param id
     * @return
     */
    AdminRoom selectAdminWithRoom(Integer id);
}
