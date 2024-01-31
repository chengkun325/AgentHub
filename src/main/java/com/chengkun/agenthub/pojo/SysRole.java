package com.chengkun.agenthub.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@TableName("sys_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRole implements Serializable {
    @Serial
    private static final long serialVersionUID = -54979041104113736L;

    @TableId("id")
    private Long roleId;

    private String roleName;

    private String roleKey;

    private String status;

    private Integer delFlag;

    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;
    private String remark;

}
