package com.chengkun.agenthub.annotation;

import java.lang.annotation.*;

/* 
 * 该注解用于标识某个方法的optType。
 */
@Target(ElementType.METHOD) // 只能作用在方法上。
@Retention(RetentionPolicy.RUNTIME) // 保留策略是：运行时。这意味着这个注解的信息会在运行时保留，可以通过反射机制读取。
@Documented // 使用该注解的元素应该被 javadoc 或类似的工具文档化。
public @interface OptLog { // @interface 自定义注解的声明。

    // 注解的属性。
    String optType() default ""; // 操作的类型。
}
