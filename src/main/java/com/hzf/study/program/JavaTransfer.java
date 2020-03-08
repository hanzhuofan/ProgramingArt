package com.hzf.study.program;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Java只有值传递，所谓的引用传递也是对象地址的值传递 Java 中的数据类型分为基本数据类型和引用数据类型。 在进行赋值操作、用作方法参数或返回值时，会有值传递和引用（地址）传递的差别。 Java 浅拷贝和深拷贝
 *
 * @author hanzhuofan
 * @since 2020/2/18 21:01
 */
public class JavaTransfer {
    public static void main(String[] args) {
        String a = "flow";
        test(a);
        System.out.println(a);

        int b = 123;
        test(b);
        System.out.println(b);
        System.out.println(a.indexOf("flower"));
        System.out.println(a.indexOf("flow"));

        Subject subject = new Subject("yuwen");
        Student studentA = new Student();
        studentA.setSubject(subject);
        studentA.setName("Lynn");
        studentA.setAge(20);
        Student studentB = (Student)studentA.clone();
        studentB.setName("Lily");
        studentB.setAge(18);
        System.out.println("studentA:" + studentA.toString());
        System.out.println("studentB:" + studentB.toString());
        Subject subjectB = studentB.getSubject();
        subjectB.setName("lishi");
        System.out.println("studentA:" + studentA.toString());
        System.out.println("studentB:" + studentB.toString());

        try {
            System.out.println(ClassLoader.getSystemClassLoader());
            System.out.println(ClassLoader.getSystemClassLoader().getParent());
            System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
            URL[] extURLs = ((URLClassLoader)ClassLoader.getSystemClassLoader()).getURLs();

            for (int i = 0; i < extURLs.length; i++) {
                System.out.println(extURLs[i]);
            }
            extURLs = ((URLClassLoader)ClassLoader.getSystemClassLoader().getParent()).getURLs();

            for (int i = 0; i < extURLs.length; i++) {
                System.out.println(extURLs[i]);
            }
            // 查看当前系统类路径中包含的路径条目
            System.out.println(System.getProperty("java.class.path"));
            Class typeLoaded = Class.forName("com.hzf.study.program.JavaTransfer");
            System.out.println(typeLoaded.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test(String a) {
        a = new String("456");
        System.out.println(a);
    }

    private static void test(int b) {
        b = 124;
        System.out.println(b);
    }

    static class Subject {

        private String name;

        public Subject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "[Subject: " + this.hashCode() + ",name:" + name + "]";
        }
    }

    static class Student implements Cloneable {

        // 引用类型
        private Subject subject;
        // 基础数据类型
        private String name;
        private int age;

        public Subject getSubject() {
            return subject;
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        /**
         * 重写clone()方法
         *
         * @return
         */
        @Override
        public Object clone() {
            // 浅拷贝
            try {
                // 直接调用父类的clone()方法
                return super.clone();
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }

        @Override
        public String toString() {
            return "[Student: " + this.hashCode() + ",subject:" + subject + ",name:" + name + ",age:" + age + "]";
        }
    }
}
