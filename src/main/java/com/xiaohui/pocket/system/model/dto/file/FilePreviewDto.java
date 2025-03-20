package com.xiaohui.pocket.system.model.dto.file;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/3/19
 */
@Data
public class FilePreviewDto {

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 请求响应对象
     */
    private HttpServletResponse response;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

}
