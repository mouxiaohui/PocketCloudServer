package com.xiaohui.pocket.system.model.form.recycle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 文件删除参数实体
 *
 * @author xiaohui
 * @since 2025/3/27
 */
@Schema(description = "文件删除参数实体")
@Data
public class DeleteForm {

    @Schema(description = "要删除的文件ID集合，多个使用公用分割符分隔")
    @NotBlank(message = "请选择要删除的文件")
    private String fileIds;

}
