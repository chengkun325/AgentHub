package com.chengkun.agenthub.handler;

import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.constant.CommonConstant;
import com.chengkun.agenthub.model.vo.ResultVO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* 
 * 定义了在用户访问受保护资源时，如果权限不足时的处理逻辑。它负责向客户端返回适当的错误信息，
 * 告知用户当前操作所需的权限。它确保当用户试图访问他们没有权限的资源时，系统能够正确地响应并通知用户相关的错误信息。
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    /* 
     * 当用户尝试访问受保护资源，但由于缺乏足够的权限而被拒绝时，Spring Security 将调用该方法。
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 设置响应的内容类型为 JSON 格式
        response.setContentType(CommonConstant.APPLICATION_JSON);
        // 客户端就会收到一个包含权限不足信息的 JSON 响应，通常用于前端或其他客户端显示给用户。
        response.getWriter().write(JSON.toJSONString(ResultVO.fail("权限不足")));
    }
}
