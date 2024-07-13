package com.chengkun.agenthub.aspect;

import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.entity.ExceptionLog;
import com.chengkun.agenthub.event.ExceptionLogEvent;
import com.chengkun.agenthub.util.ExceptionUtil;
import com.chengkun.agenthub.util.IpUtil;

import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/* 
 * 切面类，用于记录控制器方法抛出的异常日志。
 */
@Aspect // 表示这是一个切面类，用于处理横切关注点。
@Component //将该类声明为 Spring 的一个组件，使其可以被 Spring 容器管理。
public class ExceptionLogAspect {

    @Autowired
    private ApplicationContext applicationContext;

    // 定义一个切入点，匹配 com.chengkun.agenthub.controller 包及其子包下的所有方法。
    @Pointcut("execution(* com.chengkun.agenthub.controller..*.*(..))")
    public void exceptionLogPointcut() {
    }

    // 定义一个通知，在切入点方法抛出异常后执行。value 指定切入点，throwing 指定抛出的异常参数名。
    /**
     * @param joinPoint
     * @param e
     * 这个方法在目标方法抛出异常后执行，用于记录异常日志。
     */
    @AfterThrowing(value = "exceptionLogPointcut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Exception e) {
        // 获取当前请求的属性。
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 获取 HTTP 请求对象。
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        // 创建一个新的 ExceptionLog 对象，用于保存异常日志信息。
        ExceptionLog exceptionLog = new ExceptionLog();
        // 获取方法签名。
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取目标方法。
        Method method = signature.getMethod();
        // 获取方法上的 ApiOperation 注解（如果有）。
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        // 设置请求的 URI。
        exceptionLog.setOptUri(Objects.requireNonNull(request).getRequestURI());
        // 获取目标类名和方法名，并拼接成完整的方法名。
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        methodName = className + "." + methodName;
        // 设置方法名。
        exceptionLog.setOptMethod(methodName);
        // 设置请求方法（GET、POST 等）。
        exceptionLog.setRequestMethod(Objects.requireNonNull(request).getMethod());
        // 根据方法参数类型设置 exceptionLog.setRequestParam：
        // 如果参数是 MultipartFile，则设置为 "file"。
        // 否则，将参数序列化为 JSON 字符串。
        if (joinPoint.getArgs().length > 0) {
            if (joinPoint.getArgs()[0] instanceof MultipartFile) {
                exceptionLog.setRequestParam("file");
            } else {
                exceptionLog.setRequestParam(JSON.toJSONString(joinPoint.getArgs()));
            }
        }
        // 设置操作描述 exceptionLog.setOptDesc：
        // 如果方法上有 ApiOperation 注解，则使用其 value 值。
        // 否则，设置为空字符串。
        if (Objects.nonNull(apiOperation)) {
            exceptionLog.setOptDesc(apiOperation.value());
        } else {
            exceptionLog.setOptDesc("");
        }
        // 设置异常信息，获取异常的堆栈跟踪。
        exceptionLog.setExceptionInfo(ExceptionUtil.getTrace(e));
        // 获取并设置 IP 地址及其来源：
        String ipAddress = IpUtil.getIpAddress(request);
        exceptionLog.setIpAddress(ipAddress);
        exceptionLog.setIpSource(IpUtil.getIpSource(ipAddress));
        // 发布异常日志事件，以便其他组件可以监听并处理。
        applicationContext.publishEvent(new ExceptionLogEvent(exceptionLog));
    }

}
