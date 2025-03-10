package com.xiaohui.pocket.system.model.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author xiaohui
 * @since 2025/3/9
 */
@Schema(description = "查询用户已上传的文件分片列表返回实体")
@Data
public class UploadedChunksVO {

    @Schema(description = "已上传的分片编号列表")
    private List<Integer> uploadedChunks;

}
