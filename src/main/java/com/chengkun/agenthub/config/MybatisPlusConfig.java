package com.chengkun.agenthub.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement // 这是 Spring 框架的注解，用于启用注解驱动的事务管理。
@Configuration // 这是 Spring 框架的注解，表明这个类是一个配置类，可以在 Spring 容器中注册 bean。
public class MybatisPlusConfig {

    /**
     * @return
     * 用于创建和配置 MyBatis Plus 的拦截器。
     */
    @Bean // 这是 Spring 框架的注解，表明这个方法将返回一个对象，该对象将被 Spring 容器管理。
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 向拦截器中添加分页拦截器。并指定数据库类型为 MySQL。
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}