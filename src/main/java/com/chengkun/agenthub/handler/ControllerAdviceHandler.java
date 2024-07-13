package com.chengkun.agenthub.handler;

import com.chengkun.agenthub.model.vo.ResultVO;
import com.chengkun.agenthub.enums.StatusCodeEnum;
import com.chengkun.agenthub.exception.BizException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;


@RestControllerAdvice // 这是 Spring 提供的注解，结合了 @ControllerAdvice 和 @ResponseBody，用于全局处理控制器中的异常，并以 JSON 格式返回响应。
public class ControllerAdviceHandler {

    /**
     * @param e
     * @return
     * 当发生 BizException 异常时，该方法将被调用，并返回一个 ResultVO 对象，包含异常的错误代码和错误信息。
     */
    @ExceptionHandler(value = BizException.class) // 指定该方法处理 BizException 类型的异常。
    public ResultVO<?> errorHandler(BizException e) {
        return ResultVO.fail(e.getCode(), e.getMessage());
    }

    /**
     * @param e
     * @return
     * 当发生 MethodArgumentNotValidException 异常时（通常在请求参数验证失败时发生），该方法将被调用，并返回一个 ResultVO 对象，包含验证错误代码和错误信息。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class) // 指定该方法处理 MethodArgumentNotValidException 类型的异常。
    public ResultVO<?> errorHandler(MethodArgumentNotValidException e) {
        return ResultVO.fail(StatusCodeEnum.VALID_ERROR.getCode(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * @param e
     * @return
     * 当发生其他未被具体处理的异常时，该方法将被调用，并返回一个 ResultVO 对象，包含系统错误代码和错误描述。同时，异常堆栈信息将被打印到控制台，以便调试和记录。
     */
    @ExceptionHandler(value = Exception.class) // 指定该方法处理所有类型的异常。
    public ResultVO<?> errorHandler(Exception e) {
        e.printStackTrace();
        return ResultVO.fail(StatusCodeEnum.SYSTEM_ERROR.getCode(), StatusCodeEnum.SYSTEM_ERROR.getDesc());
    }

}
