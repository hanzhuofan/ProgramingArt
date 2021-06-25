package com.hzf.study.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author zhuofan.han
 * @date 2021/6/25 16:48
 */
@Slf4j
@Component("mailSenderUtil")
public class MailSenderUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.personal}")
    private String personal;

    @Autowired
    private TaskExecutor taskExecutor;

    public void sendSimpleMail(String to, String subject, String content) {
        try {
            checkMail(to, subject, content);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            log.info("[Mail:sendSimpleMail]mail {} has been sent", to);
        } catch (Exception e) {
            log.error("[Mail:sendHtmlMail]An exception occurred while sending {} mail.", to, e);
        }
    }

    public void sendHtmlMail(String to, String subject, String content) {
        try {
            checkMail(to, subject, content);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper;
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(from, personal);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            mailSender.send(messageHelper.getMimeMessage());
            log.info("[Mail:sendHtmlMail]mail {} has been sent", to);
        } catch (Exception e) {
            log.error("[Mail:sendHtmlMail]An exception occurred while sending {} mail.", to, e);
        }
    }

    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        try {
            checkMail(to, subject, content);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper;
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(from, personal);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            messageHelper.addAttachment(fileName, file);
            mailSender.send(messageHelper.getMimeMessage());
            log.info("[Mail:sendAttachmentsMail]mail {} has been sent", to);
        } catch (Exception e) {
            log.error("[Mail:sendAttachmentsMail]An exception occurred while sending {} mail.", to, e);
        }
    }

    private void checkMail(String to, String subject, String content) {
        if (StringUtils.hasLength(to)) {
            throw new RuntimeException("ErrorMsg.USER_EMAIL_TO_NOT_EMPTY");
        }
        if (StringUtils.hasLength(subject)) {
            throw new RuntimeException("ErrorMsg.USER_EMAIL_SUBJECT_NOT_EMPTY");
        }
        if (StringUtils.hasLength(content)) {
            throw new RuntimeException("ErrorMsg.USER_EMAIL_CONTENT_NOT_EMPTY");
        }
    }

    public void sendSimpleMailByAsynchronous(String to, String subject, String content) {
        checkMail(to, subject, content);
        taskExecutor.execute(() -> sendSimpleMail(to, subject, content));
    }

    public void sendHtmlMailByAsynchronous(String to, String subject, String content) {
        checkMail(to, subject, content);
        taskExecutor.execute(() -> sendHtmlMail(to, subject, content));
    }

    public void sendAttachmentsMailByAsynchronous(String to, String subject, String content, String filePath) {
        checkMail(to, subject, content);
        taskExecutor.execute(() -> sendAttachmentsMail(to, subject, content, filePath));
    }
}
