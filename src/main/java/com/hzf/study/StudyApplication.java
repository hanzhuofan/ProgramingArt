package com.hzf.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhuofan.han
 */
@MapperScan({"com.hzf.study.security.mapper*"})
@SpringBootApplication(exclude = {
        FlywayAutoConfiguration.class
})
@EnableScheduling
public class StudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

}
