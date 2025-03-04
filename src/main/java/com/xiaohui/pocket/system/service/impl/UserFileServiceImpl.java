package com.xiaohui.pocket.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohui.pocket.common.exception.BusinessException;
import com.xiaohui.pocket.common.result.ResultCode;
import com.xiaohui.pocket.system.constants.FileConstants;
import com.xiaohui.pocket.system.converter.FileConverter;
import com.xiaohui.pocket.system.enums.FolderFlagEnum;
import com.xiaohui.pocket.system.mapper.UserFileMapper;
import com.xiaohui.pocket.system.model.dto.CreateFolderDto;
import com.xiaohui.pocket.system.model.dto.QueryFileListDto;
import com.xiaohui.pocket.system.model.entity.UserFile;
import com.xiaohui.pocket.system.service.UserFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaohui
 * @since 2025/2/28
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserFileServiceImpl extends ServiceImpl<UserFileMapper, UserFile> implements UserFileService {

    private final FileConverter fileConverter;

    /**
     * 创建用户文件夹
     *
     * @param createFolderDto 文件夹信息
     */
    @Override
    public void createFolder(CreateFolderDto createFolderDto) {
        UserFile userFile = fileConverter.toEntity(createFolderDto);
        userFile.setFolderFlag(FolderFlagEnum.YES.getCode());
        userFile.setCreateUser(createFolderDto.getUserId());
        userFile.setUpdateUser(createFolderDto.getUserId());

        if (!save(userFile)) {
            throw new BusinessException(ResultCode.CREATE_FOLDER_FAILED);
        }
    }

    /**
     * 查询用户的根文件夹信息
     *
     * @param userId 用户ID
     * @return 用户根文件夹信息
     */
    @Override
    public UserFile getUserRootFolder(Long userId) {
        LambdaQueryWrapper<UserFile> queryWrapper = new LambdaQueryWrapper<UserFile>()
                .eq(UserFile::getUserId, userId)
                .eq(UserFile::getParentId, FileConstants.TOP_PARENT_ID)
                .eq(UserFile::getFolderFlag, FolderFlagEnum.YES.getCode());
        return getOne(queryWrapper);
    }

    /**
     * 查询文件信息列表
     *
     * @param queryFileListDto 查询文件信息列表参数
     * @return 文件信息列表
     */
    @Override
    public List<UserFile> getFileList(QueryFileListDto queryFileListDto) {

        return null;
    }

//    /**
//     * 单文件上传
//     *
//     * @param realFileUploadForm 文件上传参数
//     */
//    @Override
//    public void upload(RealFileUploadForm realFileUploadForm) {
//        RealFileUploadDto realFileUploadDto = realFileConverter.toUploadDto(realFileUploadForm);
//    }

}
