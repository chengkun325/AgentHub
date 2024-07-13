package com.chengkun.agenthub.consumer;

import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.model.dto.EmailDTO;
import com.chengkun.agenthub.util.EmailUtil;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static com.chengkun.agenthub.constant.RabbitMQConstant.EMAIL_QUEUE;

/* 
 * 这个类的主要功能是监听 RabbitMQ 中的 EMAIL_QUEUE 队列。当队列中有评论消息时，process 方法会被调用，
 * 将消息数据解析成 EmailDTO 对象，并通过 EmailUtil 发送邮件通知。
 * 这种方式可以用于异步处理评论通知的邮件发送，确保系统的响应速度和用户体验。
 */
@Component // 这是 Spring 的注解，表明这个类是一个 Spring 组件，会被 Spring 容器管理。
@RabbitListener(queues = EMAIL_QUEUE) // 这是 Spring AMQP 的注解，用于监听指定的队列（EMAIL_QUEUE）。当该队列中有消息时，会触发该类中的处理方法。
public class CommentNoticeConsumer {

    @Autowired // 自动注入 EmailUtil 实例，用于发送邮件。
    private EmailUtil emailUtil;

    @RabbitHandler // 表示这是一个消息处理方法。
    public void process(byte[] data) { // 表示从队列中接收到的消息数据，以字节数组的形式传递。
        // 将字节数组转换为字符串后，再解析成 EmailDTO 对象。
        EmailDTO emailDTO = JSON.parseObject(new String(data), EmailDTO.class);
        // 调用 EmailUtil 的 sendHtmlMail 方法，发送 HTML 格式的邮件。
        emailUtil.sendHtmlMail(emailDTO);
    }

}
