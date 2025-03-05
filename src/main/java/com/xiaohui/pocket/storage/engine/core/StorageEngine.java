package com.xiaohui.pocket.storage.engine.core;

import com.xiaohui.pocket.storage.engine.dto.StoreFileDto;

import java.io.IOException;
import java.util.List;

/**
 * 文件存储引擎模块的顶级接口
 *
 * @author xiaohui
 * @since 2025/3/1
 */
public interface StorageEngine {

    /**
     * 存储物理文件
     *
     * @param context 上下文信息
     * @return 文件保存后的物理路径
     * @throws IOException 文件存储异常
     */
    String store(StoreFileDto context) throws IOException;

    /**
     * 删除物理文件
     *
     * @param realFilePathList 文件的物理路径列表
     * @throws IOException 文件删除异常
     */
    void delete(List<String> realFilePathList) throws IOException;

}
