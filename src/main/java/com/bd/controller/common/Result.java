package com.bd.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.bd.entitys.enumerate.ResultCode;

/**
 * 请求成功，则code返回0
 */
public class Result<T> {

    private int code;

    private String message;

    private T data;

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean resultFlag , String message, T data) {
        if(resultFlag){
            this.code = ResultCode.SUCCESS.code();
            this.message = "ok";
        }else{
            this.code = ResultCode.ERROR.code();
            this.message = message;
        }
        this.data = data;
    }

    public static <T> Result ok(T data) {
        return new Result(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), data);
    }

    public static Result ok(String message) {
        return new Result(ResultCode.SUCCESS.code(), message, new JSONObject());
    }

    public static Result ok() {
        return new Result(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), new JSONObject());
    }

    public static Result fail(int code, String message) {
        return new Result(code, message, new JSONObject());
    }

    public static Result fail(ResultCode resultCode, String message) {
        return new Result(resultCode.code(), message, new JSONObject());
    }

    public static <T> Result fail(int code, String message, T data) {
        return new Result(code, message, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}