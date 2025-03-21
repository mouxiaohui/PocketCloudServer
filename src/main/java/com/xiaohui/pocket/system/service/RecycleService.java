package com.xiaohui.pocket.system.service;

import com.xiaohui.pocket.system.model.vo.file.UserFileVO;

import java.util.List;

/**
 * @author xiaohui
 * @since 2025/3/20
 */
public interface RecycleService {

    /**
     * 查询用户的回收站文件列表
     *
     * @param userId 用户ID
     * @return 回收站文件列表
     */
    List<UserFileVO> getRecycleFileList(Long userId);

}
