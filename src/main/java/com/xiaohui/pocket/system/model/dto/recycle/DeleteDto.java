package com.xiaohui.pocket.system.model.dto.recycle;

import lombok.Data;

import java.util.List;

/**
 * @author xiaohui
 * @since 2025/3/27
 */
@Data
public class DeleteDto {

    /**
     * 要操作的文件ID的集合
     */
    private List<Long> fileIdList;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

}
