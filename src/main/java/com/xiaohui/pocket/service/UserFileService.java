package com.xiaohui.pocket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohui.pocket.model.dto.FolderDto;
import com.xiaohui.pocket.model.entity.UserFile;

/**
 * 用户文件信息服务接口
 *
 * @author xiaohui
 * @since 2025/2/28
 */
public interface UserFileService extends IService<UserFile> {

    /**
     * 创建文件夹
     *
     * @param folderDto 文件夹信息
     */
    void createFolder(FolderDto folderDto);

}
