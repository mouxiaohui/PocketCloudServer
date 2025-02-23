package com.xiaohui.pocket.service.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.xiaohui.pocket.common.constants.RedisConstants;
import com.xiaohui.pocket.common.enums.CaptchaTypeEnum;
import com.xiaohui.pocket.common.util.RedisUtil;
import com.xiaohui.pocket.config.property.CaptchaProperties;
import com.xiaohui.pocket.model.dto.MailDto;
import com.xiaohui.pocket.model.vo.CaptchaInfoVO;
import com.xiaohui.pocket.service.CodeService;
import com.xiaohui.pocket.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务接口
 *
 * @author xiaohui
 * @since 2025/2/23
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CodeServiceImpl implements CodeService {


    private final MailService mailService;

    private final Configuration freemarkerConfig;

    private final RedisUtil redisUtil;

    private final Font captchaFont;
    private final CaptchaProperties captchaProperties;
    private final CodeGenerator codeGenerator;

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
        if (redisUtil.getCacheObject(redisCacheKey) != null) {
            return true;
        }

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

    /**
     * 获取图片验证码
     *
     * @return 验证码
     */
    @Override
    public CaptchaInfoVO getCaptcha() {
        String captchaType = captchaProperties.getType();
        AbstractCaptcha captcha = getAbstractCaptcha(captchaType);
        captcha.setGenerator(codeGenerator);
        captcha.setTextAlpha(captchaProperties.getTextAlpha());
        captcha.setFont(captchaFont);

        String captchaCode = captcha.getCode();
        String imageBase64Data = captcha.getImageBase64Data();

        // 验证码文本缓存至Redis，用于登录校验
        String captchaKey = IdUtil.fastSimpleUUID();
        redisUtil.setCacheObject(RedisConstants.CAPTCHA_CODE_PREFIX + captchaKey
                , captchaCode, captchaProperties.getExpireSeconds(), TimeUnit.SECONDS);

        return CaptchaInfoVO.builder()
                .captchaKey(captchaKey)
                .captchaBase64(imageBase64Data)
                .build();
    }

    private AbstractCaptcha getAbstractCaptcha(String captchaType) {
        int width = captchaProperties.getWidth();
        int height = captchaProperties.getHeight();
        int interfereCount = captchaProperties.getInterfereCount();
        int codeLength = captchaProperties.getCode().getLength();

        AbstractCaptcha captcha;
        if (CaptchaTypeEnum.CIRCLE.name().equalsIgnoreCase(captchaType)) {
            captcha = CaptchaUtil.createCircleCaptcha(width, height, codeLength, interfereCount);
        } else if (CaptchaTypeEnum.GIF.name().equalsIgnoreCase(captchaType)) {
            captcha = CaptchaUtil.createGifCaptcha(width, height, codeLength);
        } else if (CaptchaTypeEnum.LINE.name().equalsIgnoreCase(captchaType)) {
            captcha = CaptchaUtil.createLineCaptcha(width, height, codeLength, interfereCount);
        } else if (CaptchaTypeEnum.SHEAR.name().equalsIgnoreCase(captchaType)) {
            captcha = CaptchaUtil.createShearCaptcha(width, height, codeLength, interfereCount);
        } else {
            throw new IllegalArgumentException("Invalid captcha type: " + captchaType);
        }
        return captcha;
    }

}
