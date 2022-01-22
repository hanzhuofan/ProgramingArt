package com.hzf.study.spring.beanlife;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuofan.han
 * @date 2022/1/22
 */
@Configuration
public class AppConfig {
    @Bean(name="beanPostProcessor")
    public MyBeanPostProcessor beanPostProcessor() {
        return new MyBeanPostProcessor();
    }

    @Bean(name="instantiationAwareBeanPostProcessor")
    public MyInstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor() {
        return new MyInstantiationAwareBeanPostProcessor();
    }

    @Bean(name="beanFactoryPostProcessor")
    public MyBeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new MyBeanFactoryPostProcessor();
    }

    @Bean(name="person", initMethod = "myInit", destroyMethod = "myDestroy")
    public Person person() {
        Person person = new Person();
        person.setName("张三");
        return person;
    }
}

