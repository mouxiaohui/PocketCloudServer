package com.xiaohui.pocket.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 文件分片合并参数对象
 *
 * @author xiaohui
 * @since 2025/3/8
 */
@Schema(description = "文件分片合并参数对象")
@Data
public class FileChunkMergeForm {

    @Schema(description = "文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    @Schema(description = "文件唯一标识")
    @NotBlank(message = "文件唯一标识不能为空")
    private String identifier;

    @Schema(description = "文件总大小")
    @NotNull(message = "文件总大小不能为空")
    private Long totalSize;

    @Schema(description = "文件的父文件夹ID")
    @NotBlank(message = "文件的父文件夹ID不能为空")
    private String parentId;

}
