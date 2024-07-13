package com.chengkun.agenthub.exception;

/* 
 * 用于处理任务相关的异常。
 */
public class TaskException extends Exception { // 受检异常，需要显式地捕获或声明抛出。

    private static final long serialVersionUID = 1L;

    private final Code code;

    public TaskException(String msg, Code code) {
        this(msg, code, null);
    }

    public TaskException(String msg, Code code, Exception exception) {
        super(msg, exception);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

    public enum Code {
        // 任务已存在、任务不存在、任务已经开始、未知错误、配置错误、任务节点不可用
        TASK_EXISTS, NO_TASK_EXISTS, TASK_ALREADY_STARTED, UNKNOWN, CONFIG_ERROR, TASK_NODE_NOT_AVAILABLE
    }
}
