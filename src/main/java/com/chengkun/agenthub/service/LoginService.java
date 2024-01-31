package com.chengkun.agenthub.service;


import com.chengkun.agenthub.pojo.SysUser;
import com.chengkun.agenthub.utils.Result;

public interface LoginService {
    Result<?> login(SysUser user);

    Result<?> logout();
}
