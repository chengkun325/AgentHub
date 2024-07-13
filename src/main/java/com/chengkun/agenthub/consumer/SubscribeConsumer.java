package com.chengkun.agenthub.consumer;


import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.entity.Article;
import com.chengkun.agenthub.entity.UserInfo;
import com.chengkun.agenthub.model.dto.EmailDTO;
import com.chengkun.agenthub.service.ArticleService;
import com.chengkun.agenthub.service.UserInfoService;
import com.chengkun.agenthub.util.EmailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.chengkun.agenthub.constant.CommonConstant.TRUE;
import static com.chengkun.agenthub.constant.RabbitMQConstant.SUBSCRIBE_QUEUE;

/* 
 * 这个类主要用于监听 RabbitMQ 中的 SUBSCRIBE_QUEUE 队列，处理文章订阅的消息通知逻辑。
 * 当有新的文章发布或更新时，通过 RabbitMQ 发布消息到该队列，触发 SubscribeConsumer 类中的处理方法，
 * 将邮件通知发送给订阅了文章更新的用户。这种方式可以实现异步处理，提高系统的响应速度和用户体验。
 */
@Component // 这是 Spring 的注解，表明这个类是一个 Spring 组件，会被 Spring 容器管理。
@RabbitListener(queues = SUBSCRIBE_QUEUE) // 这是 Spring AMQP 的注解，用于监听名为 SUBSCRIBE_QUEUE 的 RabbitMQ 队列。当队列中有消息时，会触发该类中的处理方法。
public class SubscribeConsumer {

    @Value("${website.url}") // 从配置文件中读取 website.url 属性，该属性表示网站的基础 URL 地址。
    private String websiteUrl;

    @Autowired // 自动注入 ArticleService 实例，用于获取文章信息。
    private ArticleService articleService;

    @Autowired // 自动注入 UserInfoService 实例，用于获取订阅用户信息。
    private UserInfoService userInfoService;

    @Autowired // 自动注入 EmailUtil 实例，用于发送邮件通知。
    private EmailUtil emailUtil;

    @RabbitHandler // 表示这是一个消息处理方法。
    public void process(byte[] data) { // 表示从队列中接收到的消息数据，以字节数组的形式传递。
        // 将字节数组转换为字符串后，再解析成 Integer 类型，获取文章 ID。
        Integer articleId = JSON.parseObject(new String(data), Integer.class);
        // 根据文章 ID 查询获取对应的文章信息。
        Article article = articleService.getOne(new LambdaQueryWrapper<Article>().eq(Article::getId, articleId));
        // 查询所有订阅了文章更新的用户信息列表。
        List<UserInfo> users = userInfoService.list(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getIsSubscribe, TRUE));
        // 从用户信息列表中提取邮箱地址列表。
        List<String> emails = users.stream().map(UserInfo::getEmail).collect(Collectors.toList());
        // 遍历邮箱地址列表，为每个订阅用户构造邮件通知内容并发送邮件。
        for (String email : emails) {
            // 创建 EmailDTO 对象，设置收件人邮箱、邮件主题和邮件模板。
            EmailDTO emailDTO = new EmailDTO();
            Map<String, Object> map = new HashMap<>();
            emailDTO.setEmail(email);
            emailDTO.setSubject("文章订阅");
            emailDTO.setTemplate("common.html");
            // 构建邮件内容，包含文章更新或发布的信息，以及文章链接。
            String url = websiteUrl + "/articles/" + articleId;
            if (article.getUpdateTime() == null) {
                map.put("content", "花未眠的个人博客发布了新的文章，"
                        + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
            } else {
                map.put("content", "花未眠的个人博客对《" + article.getArticleTitle() + "》进行了更新，"
                        + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
            }
            emailDTO.setCommentMap(map);
            // 调用 emailUtil.sendHtmlMail(emailDTO); 发送 HTML 格式的邮件通知给订阅用户。
            emailUtil.sendHtmlMail(emailDTO);
        }
    }

}
