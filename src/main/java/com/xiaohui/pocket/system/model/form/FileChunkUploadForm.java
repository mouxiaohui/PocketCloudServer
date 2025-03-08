package com.xiaohui.pocket.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件分片上传参数对象
 *
 * @author xiaohui
 * @since 2025/3/8
 */
@Schema(description = "文件分片上传参数对象")
@Data
public class FileChunkUploadForm {

    @Schema(description = "文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    @Schema(description = "文件唯一标识")
    @NotBlank(message = "文件唯一标识不能为空")
    private String identifier;

    @Schema(description = "总体的分片数")
    @NotNull(message = "总体的分片数不能为空")
    private Integer totalChunks;

    @Schema(description = "当前分片的下标")
    @NotNull(message = "当前分片的下标不能为空")
    private Integer chunkNumber;

    @Schema(description = "当前分片的大小")
    @NotNull(message = "当前分片的大小不能为空")
    private Long currentChunkSize;

    @Schema(description = "文件总大小")
    @NotNull(message = "文件总大小不能为空")
    private Long totalSize;

    @Schema(description = "分片文件实体")
    @NotNull(message = "分片文件实体不能为空")
    private MultipartFile file;

}
