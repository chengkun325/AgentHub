package com.chengkun.agenthub.handler;

import com.alibaba.fastjson2.JSON;
import com.chengkun.agenthub.utils.Result;
import com.chengkun.agenthub.utils.WebUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证过程异常处理
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        Result<Object> result = Result.fail(cn.hutool.http.HttpStatus.HTTP_UNAUTHORIZED, "认证失败请先登录！");
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response, json);
    }
}
