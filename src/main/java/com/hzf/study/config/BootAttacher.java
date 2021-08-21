package com.hzf.study.config;

//import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * @author zhuofan.han
 */
@Slf4j
@Configuration
@Primary
public class BootAttacher {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.type}")
    private String dbType;
//
//    @Bean
//    @Primary
//    @FlywayDataSource
//    public DataSource getDataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(datasourceUrl);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setDbType(dbType);
//
//        log.info("[Config:BootAttacher] DataSource: " +
//                datasourceUrl + "," +
//                username + "," +
//                password);
//
//        return dataSource;
//    }

}
