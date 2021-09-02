package com.hzf.study.service;

import org.activiti.engine.delegate.*;

/**
 * @author zhuofan.han
 * @date 2021/8/27
 */
public class ActivitiInitTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        // 初始化任务
        Object assignee = delegateTask.getVariable("assignee");
        delegateTask.setAssignee((String) assignee);
    }
}
