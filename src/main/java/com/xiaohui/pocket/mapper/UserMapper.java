package com.xiaohui.pocket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohui.pocket.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaohui
 * @since 2025/2/19
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
