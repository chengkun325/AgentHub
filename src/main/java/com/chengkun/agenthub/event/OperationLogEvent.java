package com.chengkun.agenthub.event;

import com.chengkun.agenthub.entity.OperationLog;
import org.springframework.context.ApplicationEvent;

/* 
 * 发布和监听操作日志事件。
 */
public class OperationLogEvent extends ApplicationEvent { // 继承自 ApplicationEvent，表明这是一个应用程序事件类，用于在 Spring 框架中发布和监听事件。

    public OperationLogEvent(OperationLog operationLog) {
        // 调用父类 ApplicationEvent 的构造方法，并传递 operationLog 对象。
        // ApplicationEvent 的构造方法接受一个 Object 类型的参数，表示事件的源（source）。
        // 在这里，operationLog 对象作为事件的源传递给父类。
        super(operationLog);
    }
}
