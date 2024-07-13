package com.chengkun.agenthub.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/* 
 * 实现了 MyBatis-Plus 提供的 MetaObjectHandler 接口，用于在插入和更新操作时自动填充字段。
 */
@Slf4j // 这是 Lombok 提供的注解，用于自动生成日志记录器，方便在类中使用 log 记录日志。
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /* 
     * 方法在插入操作时被调用。
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 记录日志，表明插入填充操作开始。
        log.info("start insert fill ....");
        // 是 MyBatis-Plus 提供的方法，用于在插入时自动填充字段。
        // metaObject：传入的元对象，包含当前操作的实体对象。
        // createTime"：要填充的字段名，这里是 createTime 字段。
        // LocalDateTime.class：字段的类型，这里是 LocalDateTime 类型。
        // LocalDateTime.now()：要填充的值，这里是当前时间。
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    /* 
     * 方法在更新操作时被调用。
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 记录日志，表明更新填充操作开始。
        log.info("start update fill ....");
        // strictUpdateFill 是 MyBatis-Plus 提供的方法，用于在更新时自动填充字段。
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
