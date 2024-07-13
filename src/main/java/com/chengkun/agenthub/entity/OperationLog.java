package com.chengkun.agenthub.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/* 
 * 操作日志。
 */
@Data // 自动生成所有字段的 getter 和 setter 方法，以及 toString()、equals() 和 hashCode() 方法。
@Builder // 提供了一个生成器模式的 API，用于方便地创建对象实例。
@AllArgsConstructor // 生成一个包含所有字段的构造函数。
@NoArgsConstructor// 生成一个无参构造函数。
@TableName("t_operation_log") // 、指定该类对应的数据库表名为 t_operation_log。
public class OperationLog {

    // 指定 id 字段为主键，并且其值将自动生成。
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id; // 主键 id。

    private String optModule; // 操作模块。

    private String optUri; // 操作url。

    private String optType; // 操作类型。

    private String optMethod; // 操作方法。

    private String optDesc; // 操作描述。

    private String requestMethod; // 请求方式。

    private String requestParam; // 请求参数。

    private String responseData; // 返回数据。

    private Integer userId; // 用户 id。

    private String nickname; // 用户昵称。

    private String ipAddress; // 操作 IP。

    private String ipSource; // 操作地址。

    // 指定 createTime 字段在插入记录时自动填充。
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime; // 创建时间。

    // 指定 updateTime 字段在更新记录时自动填充。
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime; // 更新时间。
}
