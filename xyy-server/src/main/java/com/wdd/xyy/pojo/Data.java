package com.wdd.xyy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: wdd
 * @date: 2022/10/27 18:37
 */
@lombok.Data
@TableName("t_data")
@AllArgsConstructor
@NoArgsConstructor
public class Data {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime time;
}
