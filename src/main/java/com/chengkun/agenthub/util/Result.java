package com.chengkun.agenthub.util;

import lombok.Data;


@Data
public class Result<T> {
    private Integer status;

    private String msg;

    private T data;

    private Result(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success(Integer status, String msg) {
        return new Result<>(status, msg, null);
    }

    public static <T> Result<T> success(Integer status, String msg, T data) {
        return new Result<>(status, msg, data);
    }

    public static <T> Result<T> fail(Integer status, String msg, T data) {
        return new Result<>(status, msg, data);
    }

    public static <T> Result<T> fail(Integer status, String msg) {
        return new Result<>(status, msg, null);
    }
}
