package com.xiaohui.pocket.common.utils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Http工具类
 *
 * @author xiaohui
 * @since 2025/3/12
 */
public class HttpUtil {

    /**
     * 添加跨域的响应头
     *
     * @param response 响应对象
     */
    public static void addCorsResponseHeaders(HttpServletResponse response) {
        for (CorsConfigEnum corsConfigEnum : CorsConfigEnum.values()) {
            response.setHeader(corsConfigEnum.getKey(), corsConfigEnum.getValue());
        }
    }

    /**
     * 跨域设置枚举类
     */
    @AllArgsConstructor
    @Getter
    public enum CorsConfigEnum {
        /**
         * 允许所有远程访问
         */
        CORS_ORIGIN("Access-Control-Allow-Origin", "*"),
        /**
         * 允许认证
         */
        CORS_CREDENTIALS("Access-Control-Allow-Credentials", "true"),
        /**
         * 允许远程调用的请求类型
         */
        CORS_METHODS("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT"),
        /**
         * 指定本次预检请求的有效期，单位是秒
         */
        CORS_MAX_AGE("Access-Control-Max-Age", "3600"),
        /**
         * 允许所有请求头
         */
        CORS_HEADERS("Access-Control-Allow-Headers", "*");

        private final String key;

        private final String value;

    }

}
