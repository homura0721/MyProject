package com.example.myproject.util;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *{
 *     "code": 200,
 *     "message": "成功",
 *     "time": "2022-05-09 17:22:25",
 *     "data": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImlkIjoiMyIsImV4cCI6MTY1MjE3NDU0NX0.gfgWbIEcjhYMHT6o1g4VJIkF8Y6aCGifp373Mkwnm8w"
 * }
 */
public class Result<T> {
    //返回状态码
    private Integer code;
    //返回消息
    private String message;
    //获取当前Time时间
    private String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    //返回数据
    private T data;

    //Success结果
    public static <T> Result<T> success(T data) {
        Result<T> resultData = new Result<>();
        resultData.setCode(RequestCode.SUCCESS.getCode());
        resultData.setMessage(RequestCode.SUCCESS.getMessage());
        resultData.setData(data);
        return resultData;
    }

    //Fail结果
    public static <T> Result<T> fail(int code, String message) {
        Result<T> resultData = new Result<>();
        resultData.setCode(code);
        resultData.setMessage(message);
        return resultData;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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


