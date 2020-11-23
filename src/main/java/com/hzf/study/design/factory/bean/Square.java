package com.hzf.study.design.factory.bean;

import com.hzf.study.design.factory.Shape;

/**
 * @Author zhuofan.han
 * @Date 2020/11/23 10:16
 */
public class Square implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
