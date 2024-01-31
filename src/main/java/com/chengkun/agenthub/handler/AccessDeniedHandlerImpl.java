package com.chengkun.agenthub.handler;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson2.JSON;
import com.chengkun.agenthub.utils.Result;
import com.chengkun.agenthub.utils.WebUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权过程中出现的异常
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result<Object> result = Result.fail(HttpStatus.HTTP_FORBIDDEN, "已认证但权限不足");
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response, json);
    }
}
