package com.hzf.study.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author zhuofan.han
 * @date 2021/6/25 16:48
 */
@Slf4j
@Configuration("mailConfig")
public class MailConfig {

    @Value("classpath:mailTemplate/AccAdminPwdEmail.html")
    private Resource accAdminPwdEmail;

    @Value("classpath:mailTemplate/ValidateAccAdminEmail.html")
    private Resource validateAccAdminEmail;

    @Value("classpath:mailTemplate/ForgetPasswordEmail.html")
    private Resource forgetPasswordEmail;

    private String accAdminPwdEmailContent;

    private String validateAccAdminEmailContent;

    private String forgetPasswordEmailContent;

    @PostConstruct
    private void init() {
        try {
            accAdminPwdEmailContent = readConfigFile(accAdminPwdEmail.getFile());
            validateAccAdminEmailContent = readConfigFile(validateAccAdminEmail.getFile());
            forgetPasswordEmailContent = readConfigFile(forgetPasswordEmail.getFile());

        } catch (IOException e) {
            log.info("[InitConfig:init]Failed to parse user role authority config:" + e.getMessage());
        }
    }

    public String getAccAdminPwdEmailContent() {
        return accAdminPwdEmailContent;
    }

    public String getValidateAccAdminEmailContent() {
        return validateAccAdminEmailContent;
    }

    public String getForgetPasswordEmailContent() {
        return forgetPasswordEmailContent;
    }

    private String readConfigFile(File file) {
        StringBuilder buffer = new StringBuilder();
        try (Scanner scanner = new Scanner(file, "utf-8")) {
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {
            log.info("[InitConfig:readConfigFile]Failed to read role authority config: " + e.getMessage());
        }
        return buffer.toString();
    }
}
