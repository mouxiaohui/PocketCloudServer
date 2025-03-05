package com.xiaohui.pocket.system.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaohui
 * @since 2025/3/4
 */
@Data
public class FileSaveDto {

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件唯一标识
     */
    private String identifier;

    /**
     * 文件大小
     */
    private Long totalSize;

    /**
     * 要上传的文件实体
     */
    private MultipartFile file;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

}
