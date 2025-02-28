package com.xiaohui.pocket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohui.pocket.common.exception.BusinessException;
import com.xiaohui.pocket.common.result.ResultCode;
import com.xiaohui.pocket.converter.UserFileConverter;
import com.xiaohui.pocket.enums.FolderFlagEnum;
import com.xiaohui.pocket.mapper.UserFileMapper;
import com.xiaohui.pocket.model.dto.FolderDto;
import com.xiaohui.pocket.model.entity.UserFile;
import com.xiaohui.pocket.service.UserFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xiaohui
 * @since 2025/2/28
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserFileServiceImpl extends ServiceImpl<UserFileMapper, UserFile> implements UserFileService {

    private final UserFileConverter userFileConverter;

    /**
     * 创建用户文件夹
     *
     * @param folderDto 文件夹信息
     */
    @Override
    public void createFolder(FolderDto folderDto) {
        UserFile userFile = userFileConverter.toEntity(folderDto);
        userFile.setFolderFlag(FolderFlagEnum.YES.getCode());
        userFile.setCreateUser(folderDto.getUserId());
        userFile.setUpdateUser(folderDto.getUserId());

        if (!save(userFile)) {
            throw new BusinessException(ResultCode.CREATE_FOLDER_FAILED);
        }
        ;
    }

}
