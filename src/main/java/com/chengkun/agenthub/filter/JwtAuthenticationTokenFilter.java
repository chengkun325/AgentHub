package com.chengkun.agenthub.filter;

import com.chengkun.agenthub.pojo.dto.LoginUser;
import com.chengkun.agenthub.utils.JwtUtil;
import com.chengkun.agenthub.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.chengkun.agenthub.constants.Constants.*;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader(REQUEST_HEADER_TOKEN);
        //token为空的情况
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;//这里一定要加return，因为如果不加return，过滤器回来的时候还要走一遍，那时候还会获取token，解析token...
        }
        //token不为空，解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(ILLEGAL_TOKEN);
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        //缓存未命中
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException(USER_NOT_LOGIN);
        }
        //存入SecurityContextHolder
        //获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }

}
