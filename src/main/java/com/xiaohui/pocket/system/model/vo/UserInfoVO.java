package com.xiaohui.pocket.system.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiaohui.pocket.common.serializer.IdEncryptSerializer;
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

    @Schema(description = "用户根目录的加密ID")
    @JsonSerialize(using = IdEncryptSerializer.class) // 加密ID
    private Long rootFileId;

    @Schema(description = "用户根目录名称")
    private String rootFilename;

}
