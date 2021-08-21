package com.hzf.study.config;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuofan.han
 * @date 2021/8/20
 */
//@Configuration
public class ActivitiConfig {

    @Bean
    public StandaloneProcessEngineConfiguration processEngineConfiguration() {
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setJdbcDriver("org.postgresql.Driver");
        configuration.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        configuration.setJdbcUsername("postgres");
        configuration.setJdbcPassword("Changeme_123");
        configuration.setDatabaseSchema("spring_security");
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        return configuration;
    }
}
