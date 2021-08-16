package com.hzf.study.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuofan.han
 * @date 2021/8/16
 */
public class LombokTest {
    public static void main(String[] args) {
        LombokTest test = new LombokTest();
        AddStoreEventItem addStoreEventItem = new AddStoreEventItem();
        addStoreEventItem.setStoreId("test");
        addStoreEventItem.setStatus(123);
        addStoreEventItem.setTs(456L);
        AddStoreEvent addStoreEvent = new AddStoreEvent(test, addStoreEventItem);
        System.out.println(addStoreEvent);
        ArrayList<AddStoreEventItem> list = new ArrayList<>();
        list.add(addStoreEventItem);
        list.add(addStoreEventItem);
        AddStoreEvents addStoreEvents = new AddStoreEvents(test, list);
        System.out.println(addStoreEvents);
    }

    @Getter
    @ToString(includeFieldNames = false)
    public static class AddStoreEvent extends ApplicationEvent {
        private final AddStoreEventItem addStoreEventItem;

        public AddStoreEvent(Object source, AddStoreEventItem addStoreEventItem) {
            super(source);
            this.addStoreEventItem = addStoreEventItem;
        }
    }

    @Getter
    @ToString(includeFieldNames = false)
    public static class AddStoreEvents extends ApplicationEvent {
        private final List<AddStoreEventItem> addStoreEventItems;

        public AddStoreEvents(Object source, List<AddStoreEventItem> addStoreEventItems) {
            super(source);
            this.addStoreEventItems = addStoreEventItems;
        }
    }

    @Getter
    @ToString
    public static class UpdateStoreEvent extends ApplicationEvent {
        private final UpdateStoreEventItem updateStoreEventItem;

        public UpdateStoreEvent(Object source, UpdateStoreEventItem updateStoreEventItem) {
            super(source);
            this.updateStoreEventItem = updateStoreEventItem;
        }
    }

    @Getter
    @ToString
    public static class UpdateStoreEvents extends ApplicationEvent {
        private final List<UpdateStoreEventItem> updateStoreEventItems;

        public UpdateStoreEvents(Object source, List<UpdateStoreEventItem> updateStoreEventItems) {
            super(source);
            this.updateStoreEventItems = updateStoreEventItems;
        }
    }

    @Data
    @ToString(includeFieldNames = false)
    public static class AddStoreEventItem {
        private String storeId;
        private Integer status;
        private Long ts;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class UpdateStoreEventItem extends AddStoreEventItem {
        private Integer updateStatus;
        private Long updateTs;
    }
}
