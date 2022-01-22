package com.hzf.study.design.reactor;

/**
 * @author zhuofan.han
 * @description: event处理器的抽象类
 * @date 2022/1/20
 */
public abstract class EventHandler {
    private InputSource source;

    public abstract void handle(Event event);

    public InputSource getSource() {
        return source;
    }

    public void setSource(InputSource source) {
        this.source = source;
    }
}
