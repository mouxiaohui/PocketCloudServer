package com.xiaohui.pocket.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohui.pocket.common.constants.PocketConstants;
import com.xiaohui.pocket.common.exception.BusinessException;
import com.xiaohui.pocket.system.enums.DelFlagEnum;
import com.xiaohui.pocket.system.model.dto.file.QueryDelFileListDto;
import com.xiaohui.pocket.system.model.dto.file.QueryFileListDto;
import com.xiaohui.pocket.system.model.dto.recycle.RestoreDto;
import com.xiaohui.pocket.system.model.entity.UserFile;
import com.xiaohui.pocket.system.model.vo.file.UserFileVO;
import com.xiaohui.pocket.system.service.RecycleService;
import com.xiaohui.pocket.system.service.UserFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiaohui
 * @since 2025/3/20
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RecycleServiceImpl implements RecycleService {

    private final UserFileService userFileService;

    /**
     * 查询用户的回收站文件列表
     *
     * @param userId 用户ID
     * @return 回收站文件列表
     */
    @Override
    public List<UserFileVO> getRecycleFileList(Long userId) {
        QueryFileListDto queryFileListDto = QueryFileListDto.builder()
                .userId(userId)
                .isDeleted(DelFlagEnum.YES.getCode()).build();
        return userFileService.getFileList(queryFileListDto);
    }

    /**
     * 文件还原
     *
     * @param restoreDto 文件还原参数
     */
    @Override
    public void restore(RestoreDto restoreDto) {
        QueryDelFileListDto queryDelFileListDto = QueryDelFileListDto.builder()
                .userId(restoreDto.getUserId())
                .fileIdList(restoreDto.getFileIdList())
                .isDeleted(DelFlagEnum.YES.getCode())
                .build();
        List<UserFile> userFileList = userFileService.getDelFileList(queryDelFileListDto);
        checkRestoreFilename(restoreDto, userFileList);

        userFileList.forEach(userFile -> {
            boolean updateFlag = userFileService.undeleteFile(userFile.getUserId(), userFile.getId());
            if (!updateFlag) {
                throw new BusinessException("文件还原失败");
            }
        });
    }

    /**
     * 检查要还原的文件名称是不是被占用
     * <p>
     * 1、要还原的文件列表中有同一个文件夹下面相同名称的文件 不允许还原
     * 2、要还原的文件当前的父文件夹下面存在同名文件，我们不允许还原
     */
    private void checkRestoreFilename(RestoreDto restoreDto, List<UserFile> userFileList) {
        Set<String> filenameSet = userFileList.stream().map(record -> record.getFilename() + PocketConstants.COMMON_SEPARATOR + record.getParentId()).collect(Collectors.toSet());
        if (filenameSet.size() != userFileList.size()) {
            throw new BusinessException("文件还原失败，该还原文件中存在同名文件，请逐个还原并重命名");
        }

        for (UserFile userFile : userFileList) {
            LambdaQueryWrapper<UserFile> queryWrapper = new LambdaQueryWrapper<UserFile>()
                    .eq(UserFile::getUserId, restoreDto.getUserId())
                    .eq(UserFile::getParentId, userFile.getParentId())
                    .eq(UserFile::getFilename, userFile.getFilename())
                    .eq(UserFile::getIsDeleted, DelFlagEnum.NO.getCode());
            if (userFileService.count(queryWrapper) > 0) {
                throw new BusinessException("文件: " + userFile.getFilename() + " 还原失败，该文件夹下面已经存在了相同名称的文件或者文件夹，请重命名之后再执行文件还原操作");
            }
        }
    }

}