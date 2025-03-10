package com.xiaohui.pocket.system.model.form.file;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 文件搜索参数实体
 *
 * @author xiaohui
 * @since 2025/3/9
 */
@Schema(description = "文件搜索参数实体")
@Data
public class FileSearchForm {

    @Schema(description = "搜索的关键字")
    @NotBlank(message = "搜索关键字不能为空")
    private String keyword;

    @Schema(description = "文件类型，多个文件类型使用公用分隔符拼接")
    private String fileTypes;

}
