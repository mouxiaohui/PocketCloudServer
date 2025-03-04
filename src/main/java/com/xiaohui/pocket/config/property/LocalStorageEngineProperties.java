package com.xiaohui.pocket.config.property;

import com.xiaohui.pocket.common.utils.FileUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 本地存储配置
 *
 * @author xiaohui
 * @since 2025/3/3
 */
@Component
@ConfigurationProperties(prefix = "storage.engine.local")
@Data
public class LocalStorageEngineProperties {

    /**
     * 实际存放路径的前缀
     */
    private String rootFilePath = FileUtils.generateDefaultStoreFileRealPath();

    /**
     * 实际存放文件分片的路径的前缀
     */
    private String rootFileChunkPath = FileUtils.generateDefaultStoreFileChunkRealPath();

}
