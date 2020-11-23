package com.hzf.study.design.factory.bean;

import com.hzf.study.design.factory.Shape;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 10:16
 */
public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
