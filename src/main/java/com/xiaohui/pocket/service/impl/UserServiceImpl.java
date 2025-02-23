package com.xiaohui.pocket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohui.pocket.mapper.UserMapper;
import com.xiaohui.pocket.model.entity.User;
import com.xiaohui.pocket.model.form.UserRegisterForm;
import com.xiaohui.pocket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户服务接口
 *
 * @author xiaohui
 * @since 2025/2/19
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 用户注册业务
     *
     * @param userRegisterForm 注册用户表单
     * @return 用户ID
     */
    @Override
    public Long register(UserRegisterForm userRegisterForm) {
        return null;
    }

}
