package com.chengkun.agenthub.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration // 这是 Spring 框架的注解，表明这个类是一个配置类，可以在 Spring 容器中注册 bean。
public class RedisConfig {

    /**
     * @param factory
     * @return
     * 用于创建和配置 RedisTemplate 对象。
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置 Redis 连接工厂。
        redisTemplate.setConnectionFactory(factory);
        // 创建一个 Jackson2JsonRedisSerializer 对象，用于序列化和反序列化 Redis 的值。
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 创建一个 ObjectMapper 对象，用于 JSON 数据的处理。
        ObjectMapper mapper = new ObjectMapper();
        // 激活默认的类型信息，以便在序列化时保留类型信息。
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // 将 ObjectMapper 设置到 Jackson2JsonRedisSerializer 中。
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        // 创建一个 StringRedisSerializer 对象，用于序列化和反序列化 Redis 的键。
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 设置键的序列化器。
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // 设置哈希键的序列化器。
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 设置值的序列化器。
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // 设置哈希值的序列化器。
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 初始化 RedisTemplate。
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
