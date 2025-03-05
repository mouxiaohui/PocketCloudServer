package com.xiaohui.pocket.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohui.pocket.system.model.dto.FileSaveDto;
import com.xiaohui.pocket.system.model.entity.RealFile;

/**
 * 真实文件服务接口
 *
 * @author xiaohui
 * @since 2025/3/1
 */
public interface RealFileService extends IService<RealFile> {

    /**
     * 保存文件
     *
     * @param fileSaveDto 文件保存实体
     * @return 物理文件保存的信息
     */
    RealFile save(FileSaveDto fileSaveDto);

}
