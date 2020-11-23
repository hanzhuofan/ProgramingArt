package com.hzf.study.design.singleton;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 14:12
 */
public class SingleObject {

    //创建 SingleObject 的一个对象
    private static final SingleObject INSTANCE = new SingleObject();

    //让构造函数为 private，这样该类就不会被实例化
    private SingleObject(){}

    //获取唯一可用的对象
    public static SingleObject getInstance(){
        return INSTANCE;
    }

    public void showMessage(){
        System.out.println("Hello World!");
    }

    private volatile static SingleObject singleton;
    public static SingleObject getSingleton() {
        if (singleton == null) {
            synchronized (SingleObject.class) {
                if (singleton == null) {
                    singleton = new SingleObject();
                }
            }
        }
        return singleton;
    }
}
