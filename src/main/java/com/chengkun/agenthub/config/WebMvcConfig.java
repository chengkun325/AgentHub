package com.chengkun.agenthub.config;


import com.chengkun.agenthub.interceptor.PaginationInterceptor;
import com.chengkun.agenthub.interceptor.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration// 这是 Spring 框架的注解，表明这个类是一个配置类，可以在 Spring 容器中注册 bean。
// 实现了 WebMvcConfigurer 接口，用于自定义 Spring MVC 的配置。
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired // 这是 Spring 框架的注解，用于自动注入 Spring 容器中的 bean。
    private PaginationInterceptor paginationInterceptor;

    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) { // 配置 CORS，允许跨域请求。
        registry.addMapping("/**") // 对所有路径生效。
                .allowedOriginPatterns("*") // 允许所有来源。
                .allowCredentials(true) // 允许发送 Cookie 信息。
                .allowedHeaders("*") // 允许所有请求头。
                //.allowedOrigins("*")
                .allowedMethods("*"); // 允许所有 HTTP 方法（GET, POST, PUT, DELETE 等）。
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) { // 添加拦截器。
        registry.addInterceptor(paginationInterceptor); // 添加 paginationInterceptor 拦截器。
        registry.addInterceptor(accessLimitInterceptor); // 添加 accessLimitInterceptor 拦截器。
    }

}
