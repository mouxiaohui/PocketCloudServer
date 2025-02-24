package com.xiaohui.pocket.core.filter;

import com.xiaohui.pocket.common.utils.IPUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * 请求日志打印过滤器
 *
 * @author xiaohui
 * @since 2025/2/24
 */
@Configuration
@Slf4j
public class RequestLogFilter extends CommonsRequestLoggingFilter {

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        // 设置日志输出级别，默认debug
        return this.logger.isInfoEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        String ip = IPUtils.getIpAddr(request);
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        log.info("Request, IP: {}, {} {}", ip, method, requestURI);
        super.beforeRequest(request, message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        super.afterRequest(request, message);
    }

}