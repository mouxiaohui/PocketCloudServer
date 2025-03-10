package com.xiaohui.pocket.system.model.dto.file;

import lombok.Data;

import java.util.List;

/**
 * 批量删除文件上下文实体对象
 *
 * @author xiaohui
 * @since 2025/3/10
 */
@Data
public class DeleteFileDto {

    /**
     * 要删除的文件ID集合
     */
    private List<Long> fileIdList;

    /**
     * 当前的登录用户ID
     */
    private Long userId;

}
