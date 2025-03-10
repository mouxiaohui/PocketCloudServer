package com.xiaohui.pocket.system.model.form.file;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 批量删除文件入参对象实体
 *
 * @author xiaohui
 * @since 2025/3/10
 */
@Schema(description = "批量删除文件入参对象实体")
@Data
public class DeleteFileForm {

    @Schema(description = "要删除的文件ID，多个使用公用的分隔符分割")
    @NotBlank(message = "请选择要删除的文件信息")
    private String fileIds;

}
