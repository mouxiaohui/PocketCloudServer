package com.xiaohui.pocket.system.service;

import com.xiaohui.pocket.system.model.vo.CaptchaInfoVO;

/**
 * 验证码服务接口
 *
 * @author xiaohui
 * @since 2025/2/23
 */
public interface CodeService {

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @return 是否发送成功
     */
    boolean sendEmailCode(String email);

    /**
     * 校验邮箱验证码
     *
     * @param email 邮箱
     * @param code  验证码
     * @return 是否校验成功
     */
    boolean checkEmailCode(String email, String code);

    /**
     * 从Redis中删除邮箱验证码
     *
     * @param email 邮箱
     */
    void deleteEmailCode(String email);

    /**
     * 获取图片验证码
     *
     * @return 验证码
     */
    CaptchaInfoVO getCaptcha();

    /**
     * 校验图片验证码
     *
     * @param captcha    验证码
     * @param captchaKey 验证码key
     * @return 是否校验成功
     */
    boolean checkCaptcha(String captcha, String captchaKey);

}
