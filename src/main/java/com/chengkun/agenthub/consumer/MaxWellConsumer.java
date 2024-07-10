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

@Component
@RabbitListener(queues = MAXWELL_QUEUE)
public class MaxWellConsumer {

    @Autowired
    private ElasticsearchMapper elasticsearchMapper;

    @RabbitHandler
    public void process(byte[] data) {
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
        Article article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Article.class);
        switch (maxwellDataDTO.getType()) {
            case "insert":
            case "update":
                elasticsearchMapper.save(BeanCopyUtil.copyObject(article, ArticleSearchDTO.class));
                break;
            case "delete":
                elasticsearchMapper.deleteById(article.getId());
                break;
            default:
                break;
        }
    }
}