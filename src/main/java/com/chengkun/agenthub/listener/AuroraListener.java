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

@Component
public class AuroraListener {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    @Async
    @EventListener(OperationLogEvent.class)
    public void saveOperationLog(OperationLogEvent operationLogEvent) {
        operationLogMapper.insert((OperationLog) operationLogEvent.getSource());
    }

    @Async
    @EventListener(ExceptionLogEvent.class)
    public void saveExceptionLog(ExceptionLogEvent exceptionLogEvent) {
        exceptionLogMapper.insert((ExceptionLog) exceptionLogEvent.getSource());
    }

}
