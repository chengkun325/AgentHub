package com.chengkun.agenthub.listener;

import com.chengkun.agenthub.entity.ExceptionLog;
import com.chengkun.agenthub.entity.OperationLog;
import com.chengkun.agenthub.event.ExceptionLogEvent;
import com.chengkun.agenthub.event.OperationLogEvent;
import com.chengkun.agenthub.mapper.ExceptionLogMapper;
import com.chengkun.agenthub.mapper.OperationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/* 
 * 通过实现事件监听器，异步处理操作日志和异常日志的保存操作。
 * 它使用 Spring 的 @EventListener 注解监听特定事件，并使用 @Async 注解实现异步执行，
 * 从而提高系统的性能和响应速度。这种设计方式有助于解耦业务逻辑和日志处理，使得代码更清晰、维护更容易。
 */
@Component
public class AgentHubListener {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    /**
     * @param operationLogEvent
     * 从事件中获取操作日志对象并插入数据库。
     */
    @Async // 异步执行。即使方法执行时间较长，也不会阻塞主线程，提高系统的响应速度。
    @EventListener(OperationLogEvent.class) // 将该方法标记为监听 OperationLogEvent 事件。当事件发布时，该方法将被调用。
    public void saveOperationLog(OperationLogEvent operationLogEvent) {
        operationLogMapper.insert((OperationLog) operationLogEvent.getSource());
    }

    /**
     * @param exceptionLogEvent
     * 从事件中获取异常日志对象并插入数据库。
     */
    @Async
    @EventListener(ExceptionLogEvent.class)
    public void saveExceptionLog(ExceptionLogEvent exceptionLogEvent) {
        exceptionLogMapper.insert((ExceptionLog) exceptionLogEvent.getSource());
    }

}
