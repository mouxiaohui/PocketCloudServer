package com.xiaohui.pocket.service;

import com.xiaohui.pocket.model.dto.MailDto;

/**
 * 邮件服务接口层
 *
 * @author xiaohui
 * @since 2025/2/19
 */
public interface MailService {

    /**
     * 发送简单文本邮件
     *
     * @param mailDto 邮件信息
     * @return 发送结果
     */
    boolean sendMail(MailDto mailDto);

}
