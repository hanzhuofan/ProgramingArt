package com.hzf.study.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author zhuofan.han
 * @date 2021/8/27
 */
public class CountersignTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("会签监听");
        //获取流程id
        String exId = delegateTask.getExecutionId();
        //获取流程参数pass，会签人员完成自己的审批任务时会添加流程参数pass，false为拒绝，true为同意
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = engine.getRuntimeService();
        boolean pass = (Boolean) runtimeService.getVariable(exId, "pass");
        /*
         * false：有一个人拒绝，整个流程就结束了，
         * 	因为Complete condition的值为pass == false，即，当流程参数为pass时会签就结束开始下一个任务
         * 	所以，当pass == false时，直接设置下一个流程跳转需要的参数
         * true：审批人同意，同时要判断是不是所有的人都已经完成了，而不是由一个人同意该会签就结束
         * 	值得注意的是如果一个审批人完成了审批进入到该监听时nrOfCompletedInstances的值还没有更新，因此需要+1
         */
        if(!pass){
            //会签结束，设置参数result为N，下个任务为申请
            runtimeService.setVariable(exId, "result", "N");
        }else{
            Integer complete = (Integer) runtimeService.getVariable(exId, "nrOfCompletedInstances");
            Integer all = (Integer) runtimeService.getVariable(exId, "nrOfInstances");
            //说明都完成了并且没有人拒绝
            if((complete + 1) / all == 1){
                runtimeService.setVariable(exId, "result", "Y");
            }
        }
    }
}
