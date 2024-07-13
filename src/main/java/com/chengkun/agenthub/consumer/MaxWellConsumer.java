package com.chengkun.agenthub.consumer;

import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.model.dto.ArticleSearchDTO;
import com.chengkun.agenthub.model.dto.MaxwellDataDTO;
import com.chengkun.agenthub.entity.Article;
import com.chengkun.agenthub.mapper.ElasticsearchMapper;
import com.chengkun.agenthub.util.BeanCopyUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.chengkun.agenthub.constant.RabbitMQConstant.MAXWELL_QUEUE;

/* 
 * 这个类的主要功能是监听 RabbitMQ 中的 MAXWELL_QUEUE 队列。
 * 当队列中有消息时，process 方法会被调用，将消息数据解析成 MaxwellDataDTO 对象，
 * 并根据数据的类型（插入、更新或删除）对 Elasticsearch 进行相应的操作（保存或删除文章数据）。
 * 这种方式确保了数据在数据库和 Elasticsearch 之间的一致性。
 */
@Component // 这是 Spring 的注解，表明这个类是一个 Spring 组件，会被 Spring 容器管理。
@RabbitListener(queues = MAXWELL_QUEUE) // 这是 Spring AMQP 的注解，用于监听指定的队列（MAXWELL_QUEUE）。当该队列中有消息时，会触发该类中的处理方法。
public class MaxWellConsumer {

    @Autowired // 自动注入 ElasticsearchMapper 实例，用于与 Elasticsearch 进行数据交互。
    private ElasticsearchMapper elasticsearchMapper;

    @RabbitHandler // 表示这是一个消息处理方法。
    public void process(byte[] data) { // 表示从队列中接收到的消息数据，以字节数组的形式传递。
        // 将字节数组转换为字符串后，再解析成 MaxwellDataDTO 对象。
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
        // 将 MaxwellDataDTO 对象中的数据部分解析成 Article 对象。
        Article article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Article.class);
        // 根据 MaxwellDataDTO 对象的类型进行不同的处理：
        switch (maxwellDataDTO.getType()) {
            case "insert":
            case "update":
            // 将 Article 对象转换为 ArticleSearchDTO 对象后保存到 Elasticsearch 中。
                elasticsearchMapper.save(BeanCopyUtil.copyObject(article, ArticleSearchDTO.class));
                break;
            case "delete":
            // 调用 elasticsearchMapper.deleteById 方法，根据文章的 ID 从 Elasticsearch 中删除相应的数据。
                elasticsearchMapper.deleteById(article.getId());
                break;
            default:
            // 如果类型不匹配，什么也不做。
                break;
        }
    }
}