package com.hzf.study.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zhuofan.han
 * @Date 2020/10/26 13:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 3177602819135682156L;
    
    private T data;

    private Integer errCode;

    private String errMsg;

    public Result(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public static <T> Result<T> create(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> success() {
        return create(0, "Success");
    }

    public static <T> Result<T> success(T data) {
        return (Result<T>) success().setData(data);
    }

    public static <T> Result<T> fail() {
        return create(500, "Fail");
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        return create(code, msg);
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
}
