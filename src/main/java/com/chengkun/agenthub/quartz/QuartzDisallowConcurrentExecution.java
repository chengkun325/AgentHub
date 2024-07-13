package com.chengkun.agenthub.quartz;

import com.chengkun.agenthub.entity.Job;
import com.chengkun.agenthub.util.JobInvokeUtil;
import org.quartz.JobExecutionContext;

public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    // context：任务执行的上下文信息。
    // job：具体的任务对象。
    protected void doExecute(JobExecutionContext context, Job job) throws Exception {
        // 用于通过反射调用任务对象 job 的方法来执行具体的业务逻辑。
        JobInvokeUtil.invokeMethod(job);
    }
}
