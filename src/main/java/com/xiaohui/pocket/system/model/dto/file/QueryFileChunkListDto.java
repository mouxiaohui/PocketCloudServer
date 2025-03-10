package com.xiaohui.pocket.system.model.dto.file;

import lombok.Builder;
import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/3/8
 */
@Data
@Builder
public class QueryFileChunkListDto {

    /**
     * 创建用户id
     */
    private Long createUser;

    /**
     * 文件唯一标识
     */
    private String identifier;

}
