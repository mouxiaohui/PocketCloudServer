package com.xiaohui.pocket.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 文件重命名参数对象
 *
 * @author xiaohui
 * @since 2025/3/5
 */
@Schema(description = "文件重命名参数对象")
@Data
public class UpdateFilenameForm {

    @Schema(description = "更新的文件ID")
    @NotBlank(message = "更新的文件ID不能为空")
    private String fileId;

    @Schema(description = "新的文件名称")
    @NotBlank(message = "新的文件名称不能为空")
    private String newFilename;

}
