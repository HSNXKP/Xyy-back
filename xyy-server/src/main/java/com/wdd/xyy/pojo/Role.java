package com.wdd.xyy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wdd
 * @date: 2022/10/22 18:56
 */
@TableName(value = "t_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;

}
