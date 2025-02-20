package com.xiaohui.pocket.service.impl;

import com.xiaohui.pocket.config.property.MailProperties;
import com.xiaohui.pocket.model.dto.MailDto;
import com.xiaohui.pocket.service.MailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author xiaohui
 * @since 2025/2/19
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final MailProperties mailProperties;

    /**
     * 发送简单文本邮件
     *
     * @param mailDto 邮件信息
     * @return 发送结果
     */
    @Override
    public boolean sendMail(MailDto mailDto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // 设置邮件的发送者地址
            helper.setFrom(mailProperties.getFrom());
            // 设置邮件的接收者地址
            helper.setTo(mailDto.getTo());
            // 设置邮件的主题
            helper.setSubject(mailDto.getSubject());
            // 设置邮件的正文内容，第二个参数设为true，表示邮件内容为HTML格式
            helper.setText(mailDto.getContent(), mailDto.isHtml());

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            log.error("发送邮件失败: {}", e.getMessage());
            return false;
        }
    }

}
