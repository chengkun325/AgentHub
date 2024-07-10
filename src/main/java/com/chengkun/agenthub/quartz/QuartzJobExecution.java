package com.chengkun.agenthub.quartz;

import com.chengkun.agenthub.entity.Job;
import com.chengkun.agenthub.util.JobInvokeUtil;
import org.quartz.JobExecutionContext;

public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, Job job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
