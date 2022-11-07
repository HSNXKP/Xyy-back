package com.wdd.xyy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 自习室
 * @author: wdd
 * @date: 2022/10/29 22:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_room")
public class Room {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime dateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime expireTime;

    private Integer parentId;

    private Boolean disabled;

    @TableField(exist = false)
    private List<Room> children;


    @TableField(exist = false)
    private Admin admin;

    @TableField(exist = false)
    private  Role role;
}
