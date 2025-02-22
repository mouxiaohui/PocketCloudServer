package com.xiaohui.pocket.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohui.pocket.common.constants.RedisConstants;
import com.xiaohui.pocket.common.util.RedisUtil;
import com.xiaohui.pocket.mapper.UserMapper;
import com.xiaohui.pocket.model.dto.MailDto;
import com.xiaohui.pocket.model.entity.User;
import com.xiaohui.pocket.model.form.UserRegisterForm;
import com.xiaohui.pocket.service.MailService;
import com.xiaohui.pocket.service.UserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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

    private final MailService mailService;

    private final Configuration freemarkerConfig;

    private final RedisUtil redisUtil;

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

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @return 是否发送成功
     */
    @Override
    public boolean sendEmailCode(String email) {
        // 缓存验证码的key
        String redisCacheKey = RedisConstants.EMAIL_CODE_PREFIX + email;
        // 先查询缓存中是否有验证码
        if(redisUtil.getCacheObject(redisCacheKey) != null) {
           return true;
        };

        // 生成4位邮箱验证码
        String code = RandomUtil.randomString(4);

        // 创建一个HashMap实例用于存储邮件模型数据
        HashMap<String, String> model = new HashMap<>();
        model.put("to", email);
        model.put("code", code);

        try {
            // 从FreeMarker配置中获取邮件模板
            Template template = freemarkerConfig.getTemplate("email-code.ftl");
            // 将模型数据和模板合并生成HTML邮件内容
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            MailDto mailDto = new MailDto(email, "邮箱验证码", html, true);

            boolean result = mailService.sendMail(mailDto);
            if (result) {
                // 缓存验证码，5分钟有效
                redisUtil.setCacheObject(redisCacheKey, code, 5, TimeUnit.MINUTES);
            }
            return result;
        } catch (Exception e) {
            log.error("模板设置失败: {}", e.getMessage());
            return false;
        }
    }

}
