package com.chengkun.agenthub.interceptor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengkun.agenthub.util.PageUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

import static com.chengkun.agenthub.constant.CommonConstant.*;

/* 
 * 在请求处理前从请求参数中获取分页信息，并设置到 PageUtil 中，以便在处理分页查询时使用。
 * 在请求处理完成后，清除分页信息，保证线程安全。这个拦截器简化了分页处理的流程，
 * 使得分页信息的管理更加方便和统一。
 */
@Component
@SuppressWarnings("all")
public class PaginationInterceptor implements HandlerInterceptor {

    /* 
     * preHandle 方法在请求处理前执行。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求参数中获取当前页码 currentPage 和页面大小 pageSize。
        String currentPage = request.getParameter(CURRENT);
        // 如果 pageSize 为空，则使用默认值 DEFAULT_SIZE。
        String pageSize = Optional.ofNullable(request.getParameter(SIZE)).orElse(DEFAULT_SIZE);
        // 如果 currentPage 存在，则调用 PageUtil.setCurrentPage 设置分页信息，将当前页码和页面大小作为参数创建一个 Page 对象。
        if (!Objects.isNull(currentPage) && !StringUtils.isEmpty(currentPage)) {
            PageUtil.setCurrentPage(new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize)));
        }
        // 返回 true 表示继续处理请求。
        return true;
    }

    /* 
     *  方法在请求处理完成后执行，无论请求是否成功。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 调用 PageUtil.remove() 清除当前线程中的分页信息，防止线程复用时数据混淆。
        PageUtil.remove();
    }

}