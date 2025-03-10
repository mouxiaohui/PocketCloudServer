package com.xiaohui.pocket.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xiaohui
 * @since 2025/2/23
 */

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * 密钥
     */
    private String secret;

    /**
     * 过期时间
     */
    private Long expire;

    /**
     * 请求头
     */
    private String header;

    /**
     * 签发者
     */
    private String issuer;

}
