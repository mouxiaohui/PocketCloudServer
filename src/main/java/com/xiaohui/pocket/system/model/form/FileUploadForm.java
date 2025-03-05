package com.xiaohui.pocket.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 单文件上传参数实体对象
 *
 * @author xiaohui
 * @since 2025/3/4
 */
@Schema(description = "单文件上传参数实体对象")
@Data
public class FileUploadForm {

    @Schema(description = "文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    @Schema(description = "文件的唯一标识")
    @NotBlank(message = "文件的唯一标识不能为空")
    private String identifier;

    @Schema(description = "文件的总大小")
    @NotNull(message = "文件的总大小不能为空")
    private Long totalSize;

    @Schema(description = "文件的父文件夹ID")
    @NotBlank(message = "文件的父文件夹ID不能为空")
    private String parentId;

    @Schema(description = "文件实体", type = "file")
    @NotNull(message = "文件实体不能为空")
    private MultipartFile file;

}
