package com.xiaohui.pocket.system.model.dto.file;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author xiaohui
 * @since 2025/3/26
 */
@Data
@Builder
public class QueryDelFileListDto {

    /**
     * 当前的登录用户
     */
    private Long userId;

    /**
     * 文件ID集合
     */
    private List<Long> fileIdList;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;

}
