package com.chengkun.agenthub.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // 用于自动生成类的getter、setter、toString、hashCode和equals方法。
@Builder // 用于提供构建器模式，使对象的创建更加简洁。
@NoArgsConstructor // 用于生成无参构造函数。
@AllArgsConstructor // 用于生成包含所有字段的构造函数。
@TableName("t_exception_log") // 于指定数据库中的表名为t_exception_log。
public class ExceptionLog {

    // 用于指定id字段为主键，并且该字段的值是自增的。
    @TableId(type = IdType.AUTO)
    private Integer id; // 日志的唯一标识符。

    private String optUri; // 请求接口。

    private String optMethod; // 请求方式。

    private String requestMethod; // 请求方法。

    private String requestParam; // 请求参数。

    private String optDesc; // 操作描述。

    private String exceptionInfo; // 异常信息。

    private String ipAddress; // IP地址。

    private String ipSource; // IP来源。

    @TableField(fill = FieldFill.INSERT) // 指定字段在插入时自动填充
    private LocalDateTime createTime; // 操作时间。

}
