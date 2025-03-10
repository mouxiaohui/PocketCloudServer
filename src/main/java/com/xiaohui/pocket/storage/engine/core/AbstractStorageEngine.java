package com.xiaohui.pocket.storage.engine.core;

import cn.hutool.core.lang.Assert;
import com.xiaohui.pocket.storage.engine.dto.MergeFileDto;
import com.xiaohui.pocket.storage.engine.dto.ReadFileDto;
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

    /**
     * 检查文件分片合并的上线文实体信息
     *
     * @param mergeFileDto 文件分片合并的上线文实体信息
     */
    protected void checkMergeFileContext(MergeFileDto mergeFileDto) {
        Assert.notBlank(mergeFileDto.getFilename(), "文件名称不能为空");
        Assert.notBlank(mergeFileDto.getIdentifier(), "文件唯一标识不能为空");
        Assert.notNull(mergeFileDto.getUserId(), "当前登录用户的ID不能为空");
        Assert.notEmpty(mergeFileDto.getRealPathList(), "文件分片列表不能为空");
    }

    /**
     * 文件读取参数校验
     *
     * @param readFileDto 文件读取上下文信息
     */
    protected void checkReadFileContext(ReadFileDto readFileDto) {
        Assert.notBlank(readFileDto.getRealPath(), "文件真实存储路径不能为空");
        Assert.notNull(readFileDto.getOutputStream(), "文件的输出流不能为空");
    }

}
