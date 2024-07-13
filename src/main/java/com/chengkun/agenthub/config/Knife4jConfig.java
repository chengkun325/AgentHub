package com.chengkun.agenthub.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;

@Configuration // 这是 Spring 框架的注解，表明这个类是一个配置类，可以在 Spring 容器中注册 bean。
@EnableSwagger2WebMvc // 这是 Knife4j 的注解，用于启用 Swagger2 的 Web MVC 支持。
public class Knife4jConfig {

    /**
     * @return
     * 用于创建 Swagger 的 Docket 对象，该对象配置了 Swagger 文档生成的各项参数：
     */
    @Bean // 这是 Spring 框架的注解，表明这个方法将返回一个对象，该对象将被 Spring 容器管理。
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) // 指定文档类型为 Swagger 2。
                .protocols(Collections.singleton("https")) // 设置协议为 HTTPS。
                .host("https://localhost:3000") // 设置文档的主机地址。
                .apiInfo(apiInfo()) // 设置 API 信息，调用 apiInfo 方法获取。
                .select() // 构建一个选择器，用于指定哪些接口将生成文档。
                .apis(RequestHandlerSelectors.basePackage("com.chengkun.agenthub.controller")) // 指定生成文档的接口所在的包。
                .paths(PathSelectors.any()) // 指定生成文档的接口路径。
                .build();
    }

    /**
     * @return
     * 用于配置 API 的基本信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AgentHub文档") // 设置文档的标题。
                .description("AgentHub") // 设置文档的描述。
                .contact(new Contact("刁承坤", "", "d357086686@163.com")) // 设置联系人信息，包括名字和邮箱。
                .termsOfServiceUrl("https://localhost:3000/api") // 设置服务条款的 URL。
                .version("1.0") // 设置文档的版本。
                .build();
    }

}
