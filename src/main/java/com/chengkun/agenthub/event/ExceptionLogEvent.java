package com.chengkun.agenthub.event;

import com.chengkun.agenthub.entity.ExceptionLog;
import org.springframework.context.ApplicationEvent;

/* 
 * 在应用程序中发布和监听异常日志事件。
 */
public class ExceptionLogEvent extends ApplicationEvent { // 继承自 ApplicationEvent 表明这是一个应用程序事件类，用于在 Spring 框架中发布和监听事件。
    public ExceptionLogEvent(ExceptionLog exceptionLog) {
        // 调用父类 ApplicationEvent 的构造方法，并传递 exceptionLog 对象。
        // ApplicationEvent 的构造方法接受一个 Object 类型的参数，表示事件的源（source）。
        // 在这里，exceptionLog 对象作为事件的源传递给父类。
        super(exceptionLog);
    }
}
