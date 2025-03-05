package com.xiaohui.pocket.storage.engine.core;

import cn.hutool.core.lang.Assert;
import com.xiaohui.pocket.storage.engine.dto.StoreFileDto;

import java.util.List;

/**
 * 文件存储引擎模块公用抽象类
 * <p>
 * 具体的文件存储实现方案的公用逻辑需要抽离到该类中
 *
 * @author xiaohui
 * @since 2025/3/1
 */
public abstract class AbstractStorageEngine implements StorageEngine {

    /**
     * 校验上传物理文件的上下文信息
     *
     * @param storeFileDto 上传物理文件的上下文信息
     */
    protected void checkArguments(StoreFileDto storeFileDto) {
        Assert.notBlank(storeFileDto.getFilename(), "文件名称不能为空");
        Assert.notNull(storeFileDto.getTotalSize(), "文件的总大小不能为空");
        Assert.notNull(storeFileDto.getInputStream(), "文件不能为空");
    }

    /**
     * 校验删除物理文件列表
     *
     * @param realFilePathList 要删除的文件路径列表
     */
    protected void checkArguments(List<String> realFilePathList) {
        Assert.notEmpty(realFilePathList, "要删除的文件路径列表不能为空");
    }

}
