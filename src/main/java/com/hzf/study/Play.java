package com.hzf.study;

import com.hzf.study.controller.GreetingController;
import com.hzf.study.service.GreetingService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        Double d = 10.0;
        System.out.println(Math.floor(d) == d);
        if (Math.floor(d) == d) {
            System.out.println(d.intValue());
        }
        System.out.println(d.intValue());

        List<Integer> tmp = new ArrayList<Integer>() {
            {
                add(9);
                add(6);
                add(3);
                add(8);
                add(5);
                add(2);
                add(7);
                add(4);
                add(1);
                add(0);
            }
        };

        tmp.sort(Comparator.comparingInt(a -> a));
        tmp.sort((a, b) -> b - a);
        System.out.println(tmp);
    }
}
