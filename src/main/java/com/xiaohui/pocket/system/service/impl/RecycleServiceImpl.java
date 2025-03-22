package com.xiaohui.pocket.system.service.impl;

import com.xiaohui.pocket.system.enums.DelFlagEnum;
import com.xiaohui.pocket.system.model.dto.file.QueryFileListDto;
import com.xiaohui.pocket.system.model.vo.file.UserFileVO;
import com.xiaohui.pocket.system.service.RecycleService;
import com.xiaohui.pocket.system.service.UserFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
