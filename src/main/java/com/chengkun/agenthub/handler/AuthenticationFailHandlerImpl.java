package com.chengkun.agenthub.handler;

import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.constant.CommonConstant;
import com.chengkun.agenthub.model.vo.ResultVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理
 */
@Component
public class AuthenticationFailHandlerImpl implements AuthenticationFailureHandler {
    /* 
     * 当用户尝试登录但认证失败时（例如，提供了错误的用户名或密码），Spring Security 将调用该方法。
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        // 设置响应的内容类型为 JSON 格式
        httpServletResponse.setContentType(CommonConstant.APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(ResultVO.fail(e.getMessage())));
    }

}
