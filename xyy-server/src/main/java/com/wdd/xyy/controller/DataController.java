package com.wdd.xyy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wdd.xyy.pojo.Data;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author: wdd
 * @date: 2022/10/27 16:13
 */
@RestController
@RequestMapping("/study/data")
public class DataController {

    @Autowired
    private DataService dataService;

    /**
     * 获取公告
     * @return
     */
    @GetMapping("/")
    public Data getData(){
        // 修改信息之后 访问公告会失败
        return dataService.getById(1);
    }

    /**
     * 发布公告
     * @param data
     * @return
     */
    @PostMapping("/")
    public RespBean updateData(@RequestBody Data data){
        if (dataService.updateById(data)) {
            return RespBean.success("发布公告成功");
        }
       return RespBean.error("发布公告失败");

    }



}
