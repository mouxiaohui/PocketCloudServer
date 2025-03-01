package com.xiaohui.pocket.core.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.xiaohui.pocket.common.enums.LogModuleEnum;
import com.xiaohui.pocket.common.utils.IPUtils;
import com.xiaohui.pocket.core.context.BaseContext;
import com.xiaohui.pocket.system.model.entity.Log;
import com.xiaohui.pocket.system.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 日志切面
 *
 * @author xiaohui
 * @since 2025/2/20
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LogAspect {

    private final LogService logService;

    private final HttpServletRequest request;

    @Pointcut("@annotation(com.xiaohui.pocket.common.annotation.Log)")
    public void logPointcut() {
    }

    /**
     * 函数的环绕通知
     *
     * @param joinPoint     切点
     * @param logAnnotation 注解
     * @return 返回结果
     */
    @Around("logPointcut() && @annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, com.xiaohui.pocket.common.annotation.Log logAnnotation) throws Throwable {
        TimeInterval timer = DateUtil.timer();

        try {
            Object result = joinPoint.proceed();
            long executionTime = timer.intervalRestart(); // 计算执行时长(毫秒)
            this.saveLog(joinPoint, null, result, logAnnotation, executionTime);

            return result;
        } catch (Exception e) {
            long executionTime = timer.intervalRestart();
            this.saveLog(joinPoint, e, null, null, executionTime);
            throw e;
        }
    }

    /**
     * 保存日志
     *
     * @param joinPoint     切点
     * @param e             异常
     * @param jsonResult    结果
     * @param logAnnotation 注解
     * @param executionTime 执行时长
     */
    private void saveLog(
            final JoinPoint joinPoint, final Exception e, Object jsonResult,
            com.xiaohui.pocket.common.annotation.Log logAnnotation, Long executionTime
    ) {
        // 创建日志记录
        Log log = new Log();

        log.setExecutionTime(executionTime); // 保存执行时长(毫秒)

        if (e != null) { // 保存异常信息
            log.setModule(LogModuleEnum.EXCEPTION); // 保存模块;
            log.setContent("系统发生异常"); // 保存内容
            this.setRequestParameters(joinPoint, log); // 保存请求参数
            log.setResponseContent(JSONUtil.toJsonStr(e.getStackTrace())); // 保存响应结果
        } else {
            log.setModule(logAnnotation.module()); // 保存模块
            log.setContent(logAnnotation.value()); // 保存内容
            // 保存请求参数
            if (logAnnotation.params()) {
                this.setRequestParameters(joinPoint, log);
            }
            // 保存响应结果
            if (logAnnotation.result() && jsonResult != null) {
                log.setResponseContent(JSONUtil.toJsonStr(jsonResult));
            }
        }

        log.setRequestUri(request.getRequestURI()); // 保存请求地址

        Long userId = BaseContext.getUserId();
        if (!Objects.isNull(userId)) {
            log.setCreateBy(userId); // 保存创建人
        }

        String ipAddr = IPUtils.getIpAddr(request); // 获取ip地址
        if (StrUtil.isNotBlank(ipAddr)) {
            log.setIp(ipAddr);
            String region = IPUtils.getRegion(ipAddr);
            // 中国|0|四川省|成都市|电信 解析省和市
            if (StrUtil.isNotBlank(region)) {
                String[] regionArray = region.split("\\|");
                if (regionArray.length > 2) {
                    log.setProvince(regionArray[2]);
                    log.setCity(regionArray[3]);
                }
            }
        }

        // 获取浏览器和终端系统信息
        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentString);
        if (Objects.nonNull(userAgent)) {
            log.setOs(userAgent.getOs().getName());// 保存系统信息
            log.setBrowser(userAgent.getBrowser().getName()); // 保存浏览器信息
            log.setBrowserVersion(userAgent.getBrowser().getVersion(userAgentString)); // 保存浏览器版本
        }

        logService.save(log);
    }

    /**
     * 设置请求参数到日志对象中
     *
     * @param joinPoint 切点
     * @param log       操作日志
     */
    private void setRequestParameters(JoinPoint joinPoint, Log log) {
        String requestMethod = request.getMethod();
        log.setRequestMethod(requestMethod);
        if (HttpMethod.PUT.name().equalsIgnoreCase(requestMethod)
                || HttpMethod.POST.name().equalsIgnoreCase(requestMethod)) {
            String params = convertArgumentsToString(joinPoint.getArgs());
            log.setRequestParams(StrUtil.sub(params, 0, 65535));
        } else {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                Map<?, ?> paramsMap = (Map<?, ?>) attributes.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                log.setRequestParams(StrUtil.sub(paramsMap.toString(), 0, 65535));
            } else {
                log.setRequestParams("");
            }
        }
    }

    /**
     * 将参数数组转换为字符串
     *
     * @param paramsArray 参数数组
     * @return 参数字符串
     */
    private String convertArgumentsToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object param : paramsArray) {
                if (!shouldFilterObject(param)) {
                    params.append(JSONUtil.toJsonStr(param)).append(" ");
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param obj 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    private boolean shouldFilterObject(Object obj) {
        Class<?> clazz = obj.getClass();
        if (clazz.isArray()) {
            return MultipartFile.class.isAssignableFrom(clazz.getComponentType());
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection<?> collection = (Collection<?>) obj;
            return collection.stream().anyMatch(item -> item instanceof MultipartFile);
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map<?, ?> map = (Map<?, ?>) obj;
            return map.values().stream().anyMatch(value -> value instanceof MultipartFile);
        }
        return obj instanceof MultipartFile || obj instanceof HttpServletRequest || obj instanceof HttpServletResponse;
    }

}
