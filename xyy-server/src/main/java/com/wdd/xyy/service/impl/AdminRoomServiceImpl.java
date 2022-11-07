package com.wdd.xyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.xyy.mapper.AdminRoomMapper;
import com.wdd.xyy.pojo.AdminRoom;
import com.wdd.xyy.service.AdminRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: wdd
 * @date: 2022/10/30 20:56
 */
@Service
public class AdminRoomServiceImpl extends ServiceImpl<AdminRoomMapper, AdminRoom> implements AdminRoomService {

    @Autowired
    private AdminRoomMapper adminRoomMapper;

    /**
     * 查询当前位置是否被自己预约
     * @param adminId
     */
    @Override
    public Integer getAdminRoom(Integer adminId) {
        return adminRoomMapper.getAdminRoom(adminId);
    }
}
