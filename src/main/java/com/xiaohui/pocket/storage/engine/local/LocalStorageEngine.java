package com.xiaohui.pocket.storage.engine.local;

import com.xiaohui.pocket.common.utils.FileUtils;
import com.xiaohui.pocket.config.property.LocalStorageEngineProperties;
import com.xiaohui.pocket.storage.engine.core.AbstractStorageEngine;
import com.xiaohui.pocket.storage.engine.dto.MergeFileDto;
import com.xiaohui.pocket.storage.engine.dto.StoreFileChunkDto;
import com.xiaohui.pocket.storage.engine.dto.StoreFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

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
        checkArguments(context);
        String realFilePath = FileUtils.generateStoreFileRealPath(properties.getRootFilePath(), context.getFilename());
        FileUtils.writeStreamToFile(context.getInputStream(), new File(realFilePath), context.getTotalSize());

        return realFilePath;
    }

    /**
     * 删除物理文件
     *
     * @param realFilePathList 文件的物理路径列表
     * @throws IOException 文件删除异常
     */
    @Override
    public void delete(List<String> realFilePathList) throws IOException {
        checkArguments(realFilePathList);
        for (String realFilePath : realFilePathList) {
            org.apache.commons.io.FileUtils.forceDelete(new File(realFilePath));
        }
    }

    /**
     * 存储物理文件的分片
     *
     * @param storeFileChunkDto 存储物理文件分片上下文信息
     * @return chunk物理存储路径
     * @throws IOException 文件存储异常
     */
    @Override
    public String storeChunk(StoreFileChunkDto storeFileChunkDto) throws IOException {
        String basePath = properties.getRootFileChunkPath();
        String realFilePath = FileUtils.generateStoreFileChunkRealPath(basePath, storeFileChunkDto.getIdentifier(), storeFileChunkDto.getChunkNumber());
        FileUtils.writeStream2File(storeFileChunkDto.getInputStream(), new File(realFilePath), storeFileChunkDto.getTotalSize());
        return realFilePath;
    }

    /**
     * 合并文件分片
     *
     * @param mergeFileDto 合并文件分片上下文信息
     * @return 合并后的文件物理路径
     * @throws IOException 文件合并异常
     */
    @Override
    public String mergeFile(MergeFileDto mergeFileDto) throws IOException {
        String basePath = properties.getRootFilePath();
        String realFilePath = FileUtils.generateStoreFileRealPath(basePath, mergeFileDto.getFilename());
        FileUtils.createFile(new File(realFilePath));
        List<String> chunkPaths = mergeFileDto.getRealPathList();
        for (String chunkPath : chunkPaths) {
            FileUtils.appendWrite(Paths.get(realFilePath), new File(chunkPath).toPath());
        }
        FileUtils.deleteFiles(chunkPaths);

        return realFilePath;
    }

}
