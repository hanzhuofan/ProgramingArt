package com.hzf.study;

import com.hzf.study.controller.GreetingController;
import com.hzf.study.service.GreetingService;
import com.hzf.study.utils.DateHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println(tmp);
        tmp.sort((a, b) -> b - a);
        System.out.println(tmp);

        long now = System.currentTimeMillis();
        long epochMilli = Instant.now().toEpochMilli();
        System.out.println(now);
        System.out.println(epochMilli);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();

        System.out.println(DateFormat.getInstance().format(DateHelper.getStartOfWeek(DateHelper.getLastWeek(now))));
        System.out.println(DateFormat.getInstance().format(DateHelper.getStartOfWeek(now)));
        System.out.println(DateFormat.getInstance().format(DateHelper.getStartOfWeek(DateHelper.getNextWeek(now))));
        System.out.println(DateFormat.getInstance().format(DateHelper.getLastMonth(now)));
        System.out.println(DateFormat.getInstance().format(DateHelper.getStartOfMonth(now)));
        System.out.println(DateFormat.getInstance().format(DateHelper.getNextMonth(now)));

        Set<Test> set = new HashSet<>();
        set.add(new Test(1, 2));
        set.remove(new Test(1, 3));
        set.add(new Test(1, 3));
        System.out.println(set);
    }

    @Data
    @AllArgsConstructor
    static class Test {
        private int a;
        private int b;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Test test = (Test) o;
            return a == test.a;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a);
        }
    }
}
