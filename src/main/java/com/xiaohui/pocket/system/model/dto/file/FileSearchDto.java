package com.xiaohui.pocket.system.model.dto.file;

import lombok.Data;

import java.util.List;

/**
 * @author xiaohui
 * @since 2025/3/9
 */
@Data
public class FileSearchDto {

    /**
     * 搜索的关键字
     */
    private String keyword;

    /**
     * 搜索的文件类型集合
     */
    private List<Integer> fileTypeArray;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

}
