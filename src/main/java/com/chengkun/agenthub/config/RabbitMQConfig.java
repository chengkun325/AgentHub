package com.chengkun.agenthub.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.chengkun.agenthub.constant.RabbitMQConstant.*;

@Configuration // 这是 Spring 框架的注解，表明这个类是一个配置类，可以在 Spring 容器中注册 bean。
public class RabbitMQConfig {

    /**
     * @return
     * 创建一个名为 MAXWELL_QUEUE 的队列，队列持久化。
     */
    @Bean
    public Queue articleQueue() {
        return new Queue(MAXWELL_QUEUE, true);
    }

    /**
     * @return
     * 创建一个名为 MAXWELL_EXCHANGE 的 Fanout 类型交换机，交换机持久化，不自动删除。
     */
    @Bean
    public FanoutExchange maxWellExchange() {
        return new FanoutExchange(MAXWELL_EXCHANGE, true, false);
    }

    /**
     * @return
     * 将文章队列绑定到文章交换机上。
     */
    @Bean
    public Binding bindingArticleDirect() {
        return BindingBuilder.bind(articleQueue()).to(maxWellExchange());
    }

    /**
     * @return
     * 创建一个名为 EMAIL_QUEUE 的队列，队列持久化。
     */
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    /**
     * @return
     * 创建一个名为 EMAIL_EXCHANGE 的 Fanout 类型交换机，交换机持久化，不自动删除。
     */
    @Bean
    public FanoutExchange emailExchange() {
        return new FanoutExchange(EMAIL_EXCHANGE, true, false);
    }

    /**
     * @return
     * 将邮件队列绑定到邮件交换机上。
     */
    @Bean
    public Binding bindingEmailDirect() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange());
    }

    /**
     * @return
     * 创建一个名为 SUBSCRIBE_QUEUE 的队列，队列持久化。
     */
    @Bean
    public Queue subscribeQueue() {
        return new Queue(SUBSCRIBE_QUEUE, true);
    }

    /**
     * @return
     * 创建一个名为 SUBSCRIBE_EXCHANGE 的 Fanout 类型交换机，交换机持久化，不自动删除。
     */
    @Bean
    public FanoutExchange subscribeExchange() {
        return new FanoutExchange(SUBSCRIBE_EXCHANGE, true, false);
    }

    /**
     * @return
     * 将订阅队列绑定到订阅交换机上。
     */
    @Bean
    public Binding bindingSubscribeDirect() {
        return BindingBuilder.bind(subscribeQueue()).to(subscribeExchange());
    }

}
