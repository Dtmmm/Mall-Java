package com.dtm.mallproject.exception;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/1 18:30
 * @description : 用户不存在异常，登录时根据账号找不到对应用户时抛出此异常
 */
public class NoSuchUserException extends Exception{
    public NoSuchUserException() {}

    public NoSuchUserException(String message) {
        super(message);
    }
}
