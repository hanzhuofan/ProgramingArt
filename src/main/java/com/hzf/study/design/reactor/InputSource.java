package com.hzf.study.design.reactor;

/**
 * @author zhuofan.han
 * @description: 输入对象，reactor模式中处理的原始输入对象
 * @date 2022/1/20
 */
public class InputSource {
    private Object data;
    private long id;

    public InputSource(Object data, long id) {
        this.data = data;
        this.id = id;
    }

    @Override
    public String toString() {
        return "InputSource{" + "data=" + data + ", id=" + id + '}';
    }
}
