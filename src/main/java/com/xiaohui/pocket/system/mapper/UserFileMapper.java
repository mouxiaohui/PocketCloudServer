package com.xiaohui.pocket.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohui.pocket.system.model.dto.file.FileSearchDto;
import com.xiaohui.pocket.system.model.dto.file.QueryFileListDto;
import com.xiaohui.pocket.system.model.entity.UserFile;
import com.xiaohui.pocket.system.model.vo.file.FileSearchResultVO;
import com.xiaohui.pocket.system.model.vo.file.UserFileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaohui
 * @since 2025/2/28
 */
@Mapper
public interface UserFileMapper extends BaseMapper<UserFile> {

    /**
     * 查询用户的文件列表
     *
     * @param queryFileListDto 查询参数
     * @return 文件列表
     */
    List<UserFileVO> selectFileList(@Param("param") QueryFileListDto queryFileListDto);

    /**
     * 文件搜索
     *
     * @param fileSearchDto 文件搜索参数
     * @return 文件搜索结果
     */
    List<FileSearchResultVO> searchFile(@Param("param") FileSearchDto fileSearchDto);

}
