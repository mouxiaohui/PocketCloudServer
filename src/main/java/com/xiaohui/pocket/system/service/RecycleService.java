package com.xiaohui.pocket.system.service;

import com.xiaohui.pocket.system.model.dto.recycle.DeleteDto;
import com.xiaohui.pocket.system.model.dto.recycle.RestoreDto;
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

    /**
     * 文件还原
     *
     * @param restoreDto 文件还原参数
     */
    void restore(RestoreDto restoreDto);

    /**
     * 文件彻底删除
     *
     * @param deleteDto 文件删除参数
     */
    void delete(DeleteDto deleteDto);
}
