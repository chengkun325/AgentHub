package com.chengkun.agenthub.exception;


import com.chengkun.agenthub.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/* 
 * 在应用程序中处理业务逻辑相关的异常。
 */
@Getter // 为类中的所有字段生成 getter 方法。
@AllArgsConstructor // 包含所有字段的构造方法。
public class BizException extends RuntimeException { // 运行时异常，可以在代码中直接抛出而无需捕获。

    private Integer code = StatusCodeEnum.FAIL.getCode();

    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }

}
