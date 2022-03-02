package com.dtm.mallproject.exception;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/28 17:54
 * @description : 空参数异常，当方法参数需接收数组、集合等，如果接收到的参数为空，则抛出此异常
 */
public class EmptyParameterException extends Exception{
    public EmptyParameterException() {}

    public EmptyParameterException(String message) {
        super(message);
    }
}
