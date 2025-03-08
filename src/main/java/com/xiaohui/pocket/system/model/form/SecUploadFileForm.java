package com.xiaohui.pocket.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 秒传文件接口参数对象
 *
 * @author xiaohui
 * @since 2025/3/8
 */
@Schema(description = "秒传文件接口参数对象")
@Data
public class SecUploadFileForm {

    @Schema(description = "秒传的父文件夹ID")
    @NotBlank(message = "父文件夹ID不能为空")
    private String parentId;

    @Schema(description = "文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    @Schema(description = "文件的唯一标识")
    @NotBlank(message = "文件的唯一标识不能为空")
    private String identifier;

}
