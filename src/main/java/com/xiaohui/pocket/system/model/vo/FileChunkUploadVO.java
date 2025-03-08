package com.xiaohui.pocket.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 文件分片上传的响应信息
 *
 * @author xiaohui
 * @since 2025/3/8
 */
@Schema(description = "文件分片上传的响应信息")
@Data
@Builder
public class FileChunkUploadVO {

    @Schema(description = "是否需要合并文件 0 不需要 1 需要")
    private Integer mergeFlag;

}
