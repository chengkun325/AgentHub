package com.chengkun.agenthub.quartz;

import com.chengkun.agenthub.constant.ScheduleConstant;
import com.chengkun.agenthub.entity.Job;
import com.chengkun.agenthub.entity.JobLog;
import com.chengkun.agenthub.mapper.JobLogMapper;
import com.chengkun.agenthub.util.ExceptionUtil;
import com.chengkun.agenthub.util.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;

import static com.chengkun.agenthub.constant.CommonConstant.ONE;
import static com.chengkun.agenthub.constant.CommonConstant.ZERO;

public abstract class AbstractQuartzJob implements org.quartz.Job {

    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    // 使用 ThreadLocal 来存储任务开始执行的时间，以便在任务结束时计算任务执行的总时长。
    private static final ThreadLocal<Date> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Job job = new Job();
        // 从 JobExecutionContext 中获取任务的详细信息，并复制到一个新的 Job 对象中。
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConstant.TASK_PROPERTIES), job);
        try {
            // 调用 before 方法记录任务开始时间。
            before(context, job);
            // 调用抽象方法 doExecute 执行具体的任务逻辑。
            doExecute(context, job);
            // 调用 after 方法记录任务结束时间和日志。
            after(context, job, null);
        } catch (Exception e) {
            // 如果任务执行过程中出现异常，记录异常信息，并调用 after 方法处理后续操作。
            log.error("任务执行异常:", e);
            after(context, job, e);
        }
    }

    protected void before(JobExecutionContext context, Job job) {
        // 将当前时间设置到 THREAD_LOCAL 中。
        THREAD_LOCAL.set(new Date());
    }

    protected void after(JobExecutionContext context, Job job, Exception e) {
        // 从 THREAD_LOCAL 中获取任务开始时间，并移除该线程本地变量。
        Date startTime = THREAD_LOCAL.get();
        THREAD_LOCAL.remove();
        // 创建 JobLog 对象 jobLog，并设置其属性：jobId、jobName、jobGroup、invokeTarget、
        // startTime、endTime，以及计算任务执行时间 runMs。
        final JobLog jobLog = new JobLog();
        jobLog.setJobId(job.getId());
        jobLog.setJobName(job.getJobName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setInvokeTarget(job.getInvokeTarget());
        jobLog.setStartTime(startTime);
        jobLog.setEndTime(new Date());
        long runMs = jobLog.getEndTime().getTime() - jobLog.getStartTime().getTime();
        jobLog.setJobMessage(jobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            jobLog.setStatus(ZERO);
            jobLog.setExceptionInfo(ExceptionUtil.getTrace(e));
        } else {
            jobLog.setStatus(ONE);
        }
        // 最后，使用 SpringUtil.getBean(JobLogMapper.class) 获取 JobLogMapper 实例，并调用 insert(jobLog) 方法将任务执行日志插入数据库中。
        SpringUtil.getBean(JobLogMapper.class).insert(jobLog);
    }


    /**
     * @param context
     * @param job
     * @throws Exception
     * 参数 context 表示任务执行的上下文信息，job 表示任务对象。
     */
    protected abstract void doExecute(JobExecutionContext context, Job job) throws Exception;
}
