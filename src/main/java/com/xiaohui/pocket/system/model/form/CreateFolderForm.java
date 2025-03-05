package com.xiaohui.pocket.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建文件夹参数实体对象
 *
 * @author xiaohui
 * @since 2025/3/5
 */
@Schema(description = "创建文件夹参数实体对象")
@Data
public class CreateFolderForm {

    @Schema(description = "文件的父文件夹ID")
    @NotBlank(message = "父文件夹ID不能为空")
    private String parentId;

    @Schema(description = "文件夹名称")
    @NotBlank(message = "文件夹名称不能为空")
    private String folderName;

}
