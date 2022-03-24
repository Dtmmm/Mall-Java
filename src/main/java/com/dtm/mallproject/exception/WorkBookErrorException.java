package com.dtm.mallproject.exception;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/23 19:26
 * @description : WorkBook 异常，当获取到的 WorkBook 为空时，抛出此异常
 */
public class WorkBookErrorException extends Exception{
    public WorkBookErrorException() {}

    public WorkBookErrorException(String message) {
        super(message);
    }
}
