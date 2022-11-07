package com.wdd.xyy.exception;

/**
 * @author: wdd
 * @date: 2022/11/1 18:04
 */

public class AnonyException extends RuntimeException{
    public AnonyException(String message) {
        super(message);
    }

    public AnonyException(String message, Throwable cause) {
        super(message, cause);
    }
}
