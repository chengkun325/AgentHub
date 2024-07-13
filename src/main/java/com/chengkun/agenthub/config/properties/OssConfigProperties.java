package com.chengkun.agenthub.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data // 用于自动生成 getter、setter、toString、equals 和 hashCode 方法。
@Configuration // 这是 Spring 框架的注解，表明这个类是一个配置类，可以在 Spring 容器中注册 bean。
@ConfigurationProperties(prefix = "upload.oss") // 这是 Spring Boot 提供的注解，用于将属性文件中的配置值绑定到这个类的字段上。prefix 指定了属性的前缀，即属性文件中以 upload.oss 开头的配置项会被绑定到这个类的字段中。
public class OssConfigProperties {

    private String url; // OSS 服务的 URL 地址。

    private String endpoint; // OSS 服务的端点（一般是 URL 的简化形式）。

    private String accessKeyId; // 访问 OSS 的访问密钥 ID。

    private String accessKeySecret; // 访问 OSS 的密钥。

    private String bucketName; // 使用的 OSS 存储桶的名称。

}
