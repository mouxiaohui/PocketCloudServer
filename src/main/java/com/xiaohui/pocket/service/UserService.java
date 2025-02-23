package com.xiaohui.pocket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohui.pocket.model.entity.User;
import com.xiaohui.pocket.model.form.UserRegisterForm;

/**
 * 用户服务接口
 *
 * @author xiaohui
 * @since 2025/2/19
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册业务
     *
     * @param userRegisterForm 注册用户表单
     * @return 用户ID
     */
    Long register(UserRegisterForm userRegisterForm);

}
