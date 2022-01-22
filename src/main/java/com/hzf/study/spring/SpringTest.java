package com.hzf.study.spring;

import com.hzf.study.spring.beanlife.AppConfig;
import com.hzf.study.spring.beanlife.Person;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author zhuofan.han
 * @date 2022/1/22
 */
public class SpringTest {
    public static void main(String[] args) {
        System.out.println("现在开始初始化容器");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.scan("com.hzf.study.spring");
        context.refresh();
        System.out.println("容器初始化成功");
        Person person = context.getBean("person", Person.class);
        System.out.println(person);
        System.out.println("现在开始关闭容器！");
        context.registerShutdownHook();
    }
}
