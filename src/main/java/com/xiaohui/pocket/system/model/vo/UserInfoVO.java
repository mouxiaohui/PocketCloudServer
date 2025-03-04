package com.xiaohui.pocket.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/2/26
 */
@Schema(description = "用户信息")
@Data
@Builder
public class UserInfoVO {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "邮箱")
    private String email;

}
