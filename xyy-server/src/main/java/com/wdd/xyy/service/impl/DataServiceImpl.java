package com.wdd.xyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.xyy.mapper.DataMapper;
import com.wdd.xyy.pojo.Data;
import com.wdd.xyy.service.DataService;
import org.springframework.stereotype.Service;

/**
 * @author: wdd
 * @date: 2022/10/27 21:17
 */
@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, Data> implements DataService {


    /**
     * 获取公告
     * @return
     */
    @Override
    public Data getData() {
        return null;
    }
}
