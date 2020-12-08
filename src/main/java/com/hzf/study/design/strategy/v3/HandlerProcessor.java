package com.hzf.study.design.strategy.v3;

import com.hzf.study.design.strategy.v3.annotation.HandlerType;
import com.hzf.study.utils.ClassScaner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhuofan.han
 * @Date 2020/12/7 14:25
 */
@Component
public class HandlerProcessor implements BeanFactoryPostProcessor {
    private static final String HANDLER_PACKAGE = "com.hzf.study.design.strategy.v3.bean";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, Class> handlerMap = new HashMap<>();
        ClassScaner.scan(HANDLER_PACKAGE, HandlerType.class).forEach(clazz -> {
            // 获取注解中的类型值
            String type = clazz.getAnnotation(HandlerType.class).value();
            // 将注解中的类型值作为key，对应的类作为value，保存在Map中
            handlerMap.put(type, clazz);
        });
        // 初始化HandlerContext，将其注册到spring容器中
        Context context = new Context(handlerMap);
        beanFactory.registerSingleton("context", context);
    }
}
