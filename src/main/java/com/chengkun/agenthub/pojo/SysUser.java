package com.chengkun.agenthub.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    @TableId("id")
    private Long userId;

    private String userName;

    private String phonenumber;

    private String sex;

    private String password;

    private String status;

    private String email;

    private String nickName;

    private String avatar;

    private String userType;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private String delFlag;
}
