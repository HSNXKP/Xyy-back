package com.wdd.xyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdd.xyy.pojo.AdminRoom;

public interface AdminRoomService extends IService<AdminRoom> {

    /**
     * 查询当前位置是否被自己预约
     * @param adminId
     */
    Integer getAdminRoom(Integer adminId);
}
