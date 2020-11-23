package com.hzf.study;

import com.hzf.study.controller.GreetingController;
import com.hzf.study.service.GreetingService;

import java.lang.reflect.Method;

/**
 * @Author zhuofan.han
 * @Date 2020/11/11 17:14
 */
public class Play {
    public static void main(String[] args) throws Exception {
        GreetingController greetingController = new GreetingController();
        Class<? extends GreetingController> aClass = greetingController.getClass();

        GreetingService greetingService = new GreetingService();
        System.out.println(greetingService);

        Method setGreetingService = aClass.getDeclaredMethod("setGreetingService", GreetingService.class);
        setGreetingService.invoke(greetingController, greetingService);
        System.out.println(greetingController.getGreetingService());

    }
}
