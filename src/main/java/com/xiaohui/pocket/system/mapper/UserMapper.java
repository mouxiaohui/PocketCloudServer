package com.xiaohui.pocket.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohui.pocket.system.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaohui
 * @since 2025/2/19
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
