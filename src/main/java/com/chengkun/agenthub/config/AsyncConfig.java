package com.chengkun.agenthub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync // 这是 Spring 框架的注解，用于启用异步方法的支持。标注在配置类上，表明该类中的方法可以运行在独立的线程中。
@Configuration // 这是 Spring 框架的注解，表明这个类是一个配置类，可以在 Spring 容器中注册 bean。
public class AsyncConfig {

    @Bean // 这是 Spring 框架的注解，表明这个方法将返回一个对象，该对象将被 Spring 容器管理。
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 核心线程池大小为 10。
        executor.setMaxPoolSize(20); // 最大线程池大小为 20。
        executor.setQueueCapacity(20); // 任务队列容量为 20。当所有核心线程都在忙碌时，用于存放待执行任务的队列容量。
        executor.setKeepAliveSeconds(60); // 线程空闲时间为 60 秒。当线程数量超过核心线程数量时，多余空闲线程的存活时间，超过这个时间，多余的线程将被销毁。
        executor.setThreadNamePrefix("async-task-thread-"); // 线程名称前缀为 "async-task-thread-"。用于帮助区分和调试线程。
        return executor;
    }
}