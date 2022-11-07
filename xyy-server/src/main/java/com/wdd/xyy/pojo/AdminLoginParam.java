package com.wdd.xyy.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: wdd
 * @date: 2022/10/22 21:21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)  //禁用hashcode()
public class AdminLoginParam {

    private String username;
    private String password;
    private String code;
}
