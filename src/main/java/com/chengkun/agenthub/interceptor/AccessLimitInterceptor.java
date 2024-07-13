package com.chengkun.agenthub.interceptor;

import com.alibaba.fastjson.JSON;

import com.chengkun.agenthub.annotation.AccessLimit;
import com.chengkun.agenthub.model.vo.ResultVO;
import com.chengkun.agenthub.service.RedisService;
import com.chengkun.agenthub.util.IpUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static com.chengkun.agenthub.constant.CommonConstant.APPLICATION_JSON;

/* 
 * 实现了请求的频率限制。它在请求处理前，检查方法上的 AccessLimit 注解，根据注解中的限制参数，
 * 通过 Redis 来记录和检查请求次数。如果请求次数超过限制，则返回错误响应并记录日志。
 * 通过这种方式，可以有效防止接口被频繁调用，保护系统的稳定性和安全性。
 */
@Log4j2 // 用于自动生成日志记录器，方便在类中使用 log 记录日志。
@Component
@SuppressWarnings("all") // 抑制所有编译警告。
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    /* 
     * preHandle 方法在请求处理前执行，用于检查请求是否超过频率限制。
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        // 检查 handler 是否为 HandlerMethod 实例（表示是一个控制器方法）。
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的 AccessLimit 注解，如果存在则继续处理。
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit != null) {
                // 获取注解中的限制秒数 seconds 和最大请求次数 maxCount。
                long seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();
                // 生成一个唯一的键 key，由请求的 IP 地址和方法名称组成。
                String key = IpUtil.getIpAddress(httpServletRequest) + "-" + handlerMethod.getMethod().getName();
                try {
                    // 使用 redisService.incrExpire 方法在 Redis 中增加键的值，并设置过期时间。
                    long q = redisService.incrExpire(key, seconds);
                    // 如果请求次数超过最大请求次数，则返回错误响应，并记录警告日志。
                    if (q > maxCount) {
                        render(httpServletResponse, ResultVO.fail("请求过于频繁，" + seconds + "秒后再试"));
                        log.warn(key + "请求次数超过每" + seconds + "秒" + maxCount + "次");
                        return false;
                    }
                    // 如果没有超过限制，则允许请求继续处理。
                    return true;
                } catch (RedisConnectionFailureException e) {
                    // 如果发生 Redis 连接失败异常，则记录警告日志，并阻止请求继续处理。
                    log.warn("redis错误: " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param response
     * @param resultVO
     * @throws Exception
     * 用于向客户端返回 JSON 格式的响应。
     */
    private void render(HttpServletResponse response, ResultVO<?> resultVO) throws Exception {
        // 设置响应的内容类型为 application/json。
        response.setContentType(APPLICATION_JSON);
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(resultVO);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }

}
