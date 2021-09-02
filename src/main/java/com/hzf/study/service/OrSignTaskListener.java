package com.hzf.study.service;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.LinkedList;

/**
 * @author zhuofan.han
 * @date 2021/8/27
 */
public class OrSignTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("或签监听");
        delegateTask.addCandidateUsers(delegateTask.getVariable("candidateUsers", LinkedList.class));
        delegateTask.addCandidateGroups(delegateTask.getVariable("candidateGroups", LinkedList.class));
    }
}
