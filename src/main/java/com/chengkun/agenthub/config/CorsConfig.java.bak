package com.chengkun.agenthub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //允许跨域请求的域名
                .allowedOriginPatterns("*")
                //允许Cookies
                .allowCredentials(true)
                //允许的请求方式
                .allowedMethods("GET","POST","DELETE","PUT")
                //允许的Headers属性
                .allowedHeaders("*")
                //跨域允许时间
                .maxAge(3600);
    }
}
