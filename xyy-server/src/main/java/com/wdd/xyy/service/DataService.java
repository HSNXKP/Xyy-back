package com.wdd.xyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdd.xyy.pojo.Data;

public interface DataService extends IService<Data> {

    /**
     * 获取公告信息
     * @return
     */
    Data getData();

}
