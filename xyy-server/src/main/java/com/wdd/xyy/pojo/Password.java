package com.wdd.xyy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wdd
 * @date: 2022/10/29 18:53
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Password {

    private Integer id;
    private String oldPass;
    private String pass;
    private String checkPass;

}
