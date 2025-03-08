package com.xiaohui.pocket.storage.engine.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 合并文件上下文对象
 *
 * @author xiaohui
 * @since 2025/3/8
 */
@Data
@Builder
public class MergeFileDto {

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件唯一标识
     */
    private String identifier;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

    /**
     * 文件分片的真实存储路径集合
     */
    private List<String> realPathList;

    /**
     * 文件合并后的真实物理存储路径
     */
    private String realPath;

}
