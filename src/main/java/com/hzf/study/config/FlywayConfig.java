package com.hzf.study.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


/**
 * @author zhuofan.han
 */
@Slf4j
@Configuration
@Primary
public class FlywayConfig {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration")
                .baselineOnMigrate(true)
                .schemas("spring_security")
                .table("schema_version")
                .cleanDisabled(true)
                .load();
        flyway.migrate();
    }

}
