package com.xiaohui.pocket.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohui.pocket.system.model.dto.CreateFolderDto;
import com.xiaohui.pocket.system.model.dto.FileUploadDto;
import com.xiaohui.pocket.system.model.dto.QueryFileListDto;
import com.xiaohui.pocket.system.model.entity.UserFile;
import com.xiaohui.pocket.system.model.vo.UserFileVO;

import java.util.List;

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
     * @param createFolderDto 文件夹信息
     * @return 文件夹ID
     */
    Long createFolder(CreateFolderDto createFolderDto);

    /**
     * 查询用户的根文件夹信息
     *
     * @param userId 用户ID
     * @return 用户根文件夹信息
     */
    UserFile getUserRootFolder(Long userId);

    /**
     * 查询文件信息列表
     *
     * @param queryFileListDto 查询文件信息列表参数
     * @return 文件信息列表
     */
    List<UserFileVO> getFileList(QueryFileListDto queryFileListDto);

    /**
     * 单文件上传
     *
     * @param fileUploadDto 文件上传参数
     */
    void upload(FileUploadDto fileUploadDto);

}
