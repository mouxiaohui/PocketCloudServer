package com.xiaohui.pocket.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 图片验证码信息
 *
 * @author xiaohui
 * @since 2025/2/22
 */
@Schema(description = "图片验证码信息")
@Data
@Builder
public class CaptchaInfoVO {

    @Schema(description = "验证码缓存 Key")
    private String captchaKey;

    @Schema(description = "验证码图片Base64字符串")
    private String captchaBase64;

}
