package com.xiaohui.pocket.system.model.vo.file;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiaohui.pocket.common.serializer.IdEncryptSerializer;
import com.xiaohui.pocket.system.model.entity.UserFile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Objects;

/**
 * @author xiaohui
 * @since 2025/3/18
 */
@Schema(description = "面包屑列表展示实体")
@Data
public class BreadcrumbVO {

    @Schema(description = "文件ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long id;

    @Schema(description = "父文件夹ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long parentId;

    @Schema(description = "文件夹名称")
    private String name;

    /**
     * 用户文件实体转换为面包屑列表展示实体
     *
     * @param userFile 用户文件实体
     * @return 面包屑列表展示实体
     */
    public static BreadcrumbVO transfer(UserFile userFile) {
        BreadcrumbVO vo = new BreadcrumbVO();

        if (Objects.nonNull(userFile)) {
            vo.setId(userFile.getId());
            vo.setParentId(userFile.getParentId());
            vo.setName(userFile.getFilename());
        }

        return vo;
    }

}
