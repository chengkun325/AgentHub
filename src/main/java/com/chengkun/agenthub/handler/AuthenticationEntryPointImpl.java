package com.chengkun.agenthub.handler;

import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.constant.CommonConstant;
import com.chengkun.agenthub.model.vo.ResultVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* 
 * 用于处理未认证用户的请求。
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    /* 
     * 用于处理未认证用户的请求。当用户尝试访问受保护的资源但未进行认证时，Spring Security 将调用该方法。
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 设置响应的内容类型为 JSON 格式
        response.setContentType(CommonConstant.APPLICATION_JSON);
        // 客户端就会收到一个包含用户未登录信息的 JSON 响应，通常用于前端或其他客户端显示给用户。
        response.getWriter().write(JSON.toJSONString(ResultVO.fail(40001, "用户未登录")));
    }
}
