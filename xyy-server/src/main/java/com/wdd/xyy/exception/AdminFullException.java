package com.wdd.xyy.exception;

/**
 * @author: wdd
 * @date: 2022/11/6 13:37
 */
public class AdminFullException extends RuntimeException{
    public AdminFullException(String message) {
        super(message);
    }

    public AdminFullException(String message, Throwable cause) {
        super(message, cause);
    }
}
