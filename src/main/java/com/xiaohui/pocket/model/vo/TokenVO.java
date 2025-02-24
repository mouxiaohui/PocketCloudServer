package com.xiaohui.pocket.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 认证令牌响应对象
 *
 * @author xiaohui
 * @since 2025/2/24
 */
@Schema(description = "认证令牌响应对象")
@Data
@Builder
public class TokenVO {

    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType;

    @Schema(description = "访问令牌")
    private String accessToken;

}
