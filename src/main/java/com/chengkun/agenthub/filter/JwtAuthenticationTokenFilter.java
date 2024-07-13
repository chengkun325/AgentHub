package com.chengkun.agenthub.filter;


import com.aliyun.oss.common.comm.ServiceClient.Request;
import com.chengkun.agenthub.model.dto.UserDetailsDTO;
import com.chengkun.agenthub.service.TokenService;
import com.chengkun.agenthub.util.UserUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* 
 * JwtAuthenticationTokenFilter 类通过继承 OncePerRequestFilter 实现了 JWT 认证过滤器。
 * 在每个请求中，它会从请求中提取用户详情，如果用户未认证且存在有效的用户详情，
 * 则续期 JWT 并设置认证信息到安全上下文中。
 * 这种机制确保了每个请求都经过 JWT 认证，从而保护应用程序的安全性。
 */
@Component
@SuppressWarnings("all") // 抑制所有编译器警告。
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter { // 继承自 OncePerRequestFilter，这意味着这个过滤器会在每个请求中执行一次。

    @Autowired
    public TokenService tokenService;

    @Autowired
    public AuthenticationEntryPoint authenticationEntryPoint; // 用于处理认证失败的情况。

    /* 
     * 在每个请求中都会调用这个方法。
     */
    @SneakyThrows // 用于在方法中自动处理 checked exceptions，而无需显式地使用 try-catch 块。
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        UserDetailsDTO userDetailsDTO = tokenService.getUserDetailDTO(request);
        if (Objects.nonNull(userDetailsDTO) && Objects.isNull(UserUtil.getAuthentication())) {
            // 续期令牌并设置认证信息。
            tokenService.renewToken(userDetailsDTO);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetailsDTO, null, userDetailsDTO.getAuthorities());
            // 将该认证信息设置到当前的安全上下文中。
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 继续过滤器链。
        filterChain.doFilter(request, response);
    }
}
