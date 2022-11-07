package com.wdd.xyy.exception;

import com.wdd.xyy.pojo.RespBean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: wdd
 * @date: 2022/11/1 18:07
 */
@RestControllerAdvice
@Component
public class ExceptionHandlerAdvice {

    @ExceptionHandler(AnonyException.class)
    public RespBean anonyException(AnonyException anonyException){
        return RespBean.error("用户未登录",401);
    }
    @ExceptionHandler(AdminFullException.class)
    public RespBean adminFullException(AdminFullException adminFullException){
        return RespBean.error("该自习室或座位未绑定用户信息,请绑定再查看");
    }
}
