package com.chengkun.agenthub.aspect;

import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.annotation.OptLog;
import com.chengkun.agenthub.entity.OperationLog;
import com.chengkun.agenthub.event.OperationLogEvent;
import com.chengkun.agenthub.util.IpUtil;
import com.chengkun.agenthub.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
 * 切面类。用于记录操作日志。
 */
@Aspect // 表示这是一个切面类，用于处理横切关注点。
@Component // 将该类声明为 Spring 的一个组件，使其可以被 Spring 容器管理。
public class OperationLogAspect {

    @Autowired
    private ApplicationContext applicationContext;

    // 定义一个切入点，匹配所有被 com.chengkun.agenthub.annotation.OptLog 注解标记的方法。
    @Pointcut("@annotation(com.chengkun.agenthub.annotation.OptLog)")
    public void operationLogPointCut() {
    }

    // 定义一个通知，在切入点方法成功返回后执行。value 指定切入点，returning 指定返回值参数名。
    /**
     * @param joinPoint
     * @param keys
     * 这个方法在目标方法成功返回后执行，用于记录操作日志。
     */
    @AfterReturning(value = "operationLogPointCut()", returning = "keys")
    @SuppressWarnings("unchecked")
    public void saveOperationLog(JoinPoint joinPoint, Object keys) {
        // 获取当前请求的属性。
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 获取 HTTP 请求对象。
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        // 创建一个新的 OperationLog 对象，用于保存操作日志信息。
        OperationLog operationLog = new OperationLog();
        // 获取方法签名。
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取目标方法。
        Method method = signature.getMethod();
        // 获取类上的 Api 注解（如果有）。
        Api api = (Api) signature.getDeclaringType().getAnnotation(Api.class);
        // 获取方法上的 ApiOperation 注解（如果有）。
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        // 获取方法上的 OptLog 注解。
        OptLog optLog = method.getAnnotation(OptLog.class);
        // 设置操作模块，取 Api 注解的第一个标签。
        operationLog.setOptModule(api.tags()[0]);
        // 设置操作类型，取 OptLog 注解的 optType 属性。
        operationLog.setOptType(optLog.optType());
        // 设置操作描述，取 ApiOperation 注解的 value 属性。
        operationLog.setOptDesc(apiOperation.value());
        // 获取类名和方法名并拼接成完整的方法名：
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        methodName = className + "." + methodName;
        // 设置请求方法（GET、POST 等）。
        operationLog.setRequestMethod(Objects.requireNonNull(request).getMethod());
        operationLog.setOptMethod(methodName);
        // 根据方法参数类型设置 operationLog.setRequestParam：
        // 如果参数是 MultipartFile，则设置为 "file"。
        // 否则，将参数序列化为 JSON 字符串。
        if (joinPoint.getArgs().length > 0) {
            if (joinPoint.getArgs()[0] instanceof MultipartFile) {
                operationLog.setRequestParam("file");
            } else {
                operationLog.setRequestParam(JSON.toJSONString(joinPoint.getArgs()));
            }
        }
        // 设置返回数据，将返回值序列化为 JSON 字符串。
        operationLog.setResponseData(JSON.toJSONString(keys));
        // 设置用户信息：
        operationLog.setUserId(UserUtil.getUserDetailsDTO().getId());
        operationLog.setNickname(UserUtil.getUserDetailsDTO().getNickname());
        // 获取并设置 IP 地址及其来源：
        String ipAddress = IpUtil.getIpAddress(request);
        operationLog.setIpAddress(ipAddress);
        operationLog.setIpSource(IpUtil.getIpSource(ipAddress));
        // 设置请求的 URI。
        operationLog.setOptUri(request.getRequestURI());
        // 发布操作日志事件，以便其他组件可以监听并处理。
        applicationContext.publishEvent(new OperationLogEvent(operationLog));
    }

}
