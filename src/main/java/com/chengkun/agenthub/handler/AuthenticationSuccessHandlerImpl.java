package com.chengkun.agenthub.handler;

import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.constant.CommonConstant;
import com.chengkun.agenthub.model.dto.UserDetailsDTO;
import com.chengkun.agenthub.model.dto.UserInfoDTO;
import com.chengkun.agenthub.entity.UserAuth;
import com.chengkun.agenthub.mapper.UserAuthMapper;
import com.chengkun.agenthub.service.TokenService;
import com.chengkun.agenthub.util.BeanCopyUtil;
import com.chengkun.agenthub.util.UserUtil;
import com.chengkun.agenthub.model.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/* 
 * 它确保在用户成功登录后，系统能够生成令牌、返回用户信息并异步更新用户的认证信息，以提升用户体验和系统性能。
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private TokenService tokenService;

    /* 
     * 当用户认证成功时，Spring Security 将调用该方法。
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserInfoDTO userLoginDTO = BeanCopyUtil.copyObject(UserUtil.getUserDetailsDTO(), UserInfoDTO.class);
        if (Objects.nonNull(authentication)) {
            UserDetailsDTO userDetailsDTO = (UserDetailsDTO) authentication.getPrincipal();
            String token = tokenService.createToken(userDetailsDTO);
            userLoginDTO.setToken(token);
        }
        // 设置响应的内容类型为 JSON 格式
        response.setContentType(CommonConstant.APPLICATION_JSON);
        // 将包含用户信息和令牌的 ResultVO 对象序列化为 JSON 字符串，并写入到响应的输出流中。
        response.getWriter().write(JSON.toJSONString(ResultVO.ok(userLoginDTO)));
        updateUserInfo();
    }

    @Async // 该方法将异步执行，以提高系统的响应速度。
    public void updateUserInfo() {
        UserAuth userAuth = UserAuth.builder()
                .id(UserUtil.getUserDetailsDTO().getId())
                .ipAddress(UserUtil.getUserDetailsDTO().getIpAddress())
                .ipSource(UserUtil.getUserDetailsDTO().getIpSource())
                .lastLoginTime(UserUtil.getUserDetailsDTO().getLastLoginTime())
                .build();
        userAuthMapper.updateById(userAuth);
    }
}
