package com.chengkun.agenthub.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import static com.chengkun.agenthub.enums.ZoneEnum.SHANGHAI;

@Configuration // 这是 Spring 框架的注解，表明这个类是一个配置类，可以在 Spring 容器中注册 bean。
public class GlobalZoneConfig {

    /**
     * 设置全局时区。
     */
    @PostConstruct // 这是 Java 的注解，用于在依赖注入完成后自动调用这个方法。它通常用于进行一些初始化工作。
    public void setGlobalZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(SHANGHAI.getZone()));
    }

}
