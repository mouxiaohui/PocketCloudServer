package com.xiaohui.pocket.storage.engine.local;

import com.xiaohui.pocket.common.utils.FileUtils;
import com.xiaohui.pocket.config.property.LocalStorageEngineProperties;
import com.xiaohui.pocket.storage.engine.core.AbstractStorageEngine;
import com.xiaohui.pocket.storage.engine.dto.StoreFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 本地的文件存储引擎实现方案
 *
 * @author xiaohui
 * @since 2025/3/1
 */
@Component
@RequiredArgsConstructor
public class LocalStorageEngine extends AbstractStorageEngine {

    private final LocalStorageEngineProperties properties;

    /**
     * 存储物理文件
     *
     * @param context 上下文信息
     * @return 文件保存后的物理路径
     * @throws IOException 文件存储异常
     */
    @Override
    public String store(StoreFileDto context) throws IOException {
        checkStoreFileContext(context);
        String realFilePath = FileUtils.generateStoreFileRealPath(properties.getRootFilePath(), context.getFilename());
        FileUtils.writeStreamToFile(context.getInputStream(), new File(realFilePath), context.getTotalSize());

        return realFilePath;
    }

}
