package com.chengkun.agenthub.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data // 用于自动生成 getter、setter、toString、equals 和 hashCode 方法。
@Configuration // 这是 Spring 框架的注解，表明这个类是一个配置类，可以在 Spring 容器中注册 bean。
@ConfigurationProperties(prefix = "qq") // 这是 Spring Boot 提供的注解，用于将属性文件中的配置值绑定到这个类的字段上。prefix 指定了属性的前缀，即属性文件中以 qq 开头的配置项会被绑定到这个类的字段中。
public class QQConfigProperties {

    private String appId; // QQ 应用的唯一标识。

    private String checkTokenUrl; // 验证 QQ Token 的 URL。

    private String userInfoUrl; // 获取 QQ 用户信息的 URL。

}
