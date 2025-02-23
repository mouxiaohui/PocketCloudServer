package com.xiaohui.pocket.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 认证属性配置
 *
 * @author xiaohui
 * @since 2025/2/23
 */
@Component
@ConfigurationProperties(prefix = "auth")
@Data
public class AuthProperties {

    /**
     * 白名单 URL 集合
     */
    private String[] ignoreUrls;

}
