package com.xiaohui.pocket.core.interceptor;

import cn.hutool.json.JSONUtil;
import com.xiaohui.pocket.common.constants.JwtClaimConstants;
import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.common.result.ResultCode;
import com.xiaohui.pocket.common.utils.JwtUtils;
import com.xiaohui.pocket.config.property.JwtProperties;
import com.xiaohui.pocket.core.context.BaseContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

/**
 * jwt令牌校验的拦截器
 *
 * @author xiaohui
 * @since 2025/2/24
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    private final JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置response响应数据类型为json和编码为utf-8
        response.setContentType("application/json;charset=utf-8");

        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从请求头中获取令牌, 注意要清除前缀 "Bearer "
        String token = request.getHeader(jwtProperties.getHeader()).replace("Bearer ", "");

        // 校验令牌
        Object claim = jwtUtils.analyzeToken(token, JwtClaimConstants.USER_ID);

        if (Objects.isNull(claim)) {
            // 不通过，响应 401 状态码
            response.setStatus(401);
            String resultMsg = JSONUtil.toJsonStr(Result.failed(ResultCode.ACCESS_TOKEN_INVALID));
            response.getWriter().write(resultMsg);
            return false;
        }

        Long userId = Long.valueOf(claim.toString());
        // 将用户id存入ThreadLocal
        BaseContext.setUserId(userId);
        return true;

    }
}
