package com.xiaohui.pocket.core.interceptor;

import cn.hutool.json.JSONUtil;
import com.xiaohui.pocket.common.constants.JwtClaimConstants;
import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.common.result.ResultCode;
import com.xiaohui.pocket.common.utils.JwtUtils;
import com.xiaohui.pocket.config.property.JwtProperties;
import com.xiaohui.pocket.core.context.BaseContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
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
        // 设置response响应数据类型为json和编码为utf-8
        response.setContentType("application/json;charset=utf-8");

        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        try {
            String token;
            token = request.getHeader(jwtProperties.getHeader());
            if (token == null || token.isBlank()) {
                token = request.getParameter(jwtProperties.getHeader());
            }

            // 要清除前缀 "Bearer "
//            token = Optional.ofNullable(token)
//                    .orElse("")
//                    .replace("Bearer ", "");

//            if (token.isEmpty()) {
//                handleUnauthorizedResponse(response);
//                return false;
//            }

            // 校验令牌
            Claims claim = jwtUtils.analyzeToken(token);
            if (Objects.isNull(claim)) {
                handleUnauthorizedResponse(response);
                return false;
            }

            // 从令牌中获取用户id
            Object userClaim = claim.get(JwtClaimConstants.USER_ID);
            Long userId = ((Number) userClaim).longValue();
            // 将用户id存入ThreadLocal
            BaseContext.setUserId(userId);
            return true;

        } catch (Exception e) {
            handleUnauthorizedResponse(response);
            return false;
        }

    }

    /**
     * 处理未授权响应
     *
     * @param response Response对象
     */
    private void handleUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(401);
        String resultMsg = JSONUtil.toJsonStr(Result.failed(ResultCode.ACCESS_TOKEN_INVALID));
        response.getWriter().write(resultMsg);
    }
}
