package com.xiaohui.pocket.system.model.form.recycle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 还原回收站文件参数实体
 *
 * @author xiaohui
 * @since 2025/3/23
 */
@Schema(description = "还原回收站文件参数实体")
@Data
public class RestoreForm {

    @Schema(description = "要还原的文件ID集合，多个使用公用的分隔符分割")
    @NotBlank(message = "请选择要还原的文件")
    private String fileIds;

}
