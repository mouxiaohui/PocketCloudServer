package com.xiaohui.pocket.system.model.dto.file;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 查询文件信息列表
 *
 * @author xiaohui
 * @since 2025/3/1
 */
@Getter
@Builder
public class QueryFileListDto {

    /**
     * 父文件夹ID
     */
    private Long parentId;

    /**
     * 文件类型的集合
     */
    private List<Integer> fileTypeArray;

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
