package com.wdd.xyy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wdd
 * @date: 2022/10/30 20:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_admin_room")
public class AdminRoom {


    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer adminId;

    private Integer roomId;

}
