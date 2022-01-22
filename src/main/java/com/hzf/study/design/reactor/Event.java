package com.hzf.study.design.reactor;

/**
 * @author zhuofan.han
 * @description: reactor模式中内部处理的event类
 * @date 2022/1/20
 */
public class Event {
    private InputSource source;
    private EventType type;

    public InputSource getSource() {
        return source;
    }

    public void setSource(InputSource source) {
        this.source = source;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
