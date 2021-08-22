package com.hzf.study.service;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuofan.han
 * @date 2020/11/13 15:17
 */
@Service
@Slf4j
public class ActivitiService {
    @Autowired
    ProcessEngine processEngine;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @PostConstruct
    public void init(){
        // 3、使用RepositoryService进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/test.bpmn") // 添加bpmn资源
//                .addClasspathResource("bpmn/evection.png") // 添加png资源
                .name("出差申请流程")
                .deploy();
        // 4、输出部署信息
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    public String submitApply() {
        // 3、根据流程定义Id启动流程
        Map<String,Object> variables = new HashMap<>();
        variables.put("myid","123456789");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myTest",variables);
        // 输出内容
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());
        System.out.println("Name：" + processInstance.getName());
        System.out.println("LocalizedName：" + processInstance.getLocalizedName());
        System.out.println("ProcessDefinitionName：" + processInstance.getProcessDefinitionName());
        return "success";
    }

    public String createApply(String assignee) {
        // 根据流程key 和 任务负责人 查询任务
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("myTest") //流程Key
                .taskAssignee(assignee)//只查询该任务负责人的任务
//                .taskId("Activity_0vizqus")
                .list();
        for (Task task : list) {
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
            String myid = (String) runtimeService.getVariable(task.getExecutionId(),"myid");
            System.out.println("myid:" + myid);
            Map<String,Object> map = new HashMap<>();
            map.put("bb","BB");
            taskService.complete(task.getId(), map);
        }
        return "success";
    }

    public String secondAudit(String assignee) {
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("myTest") //流程Key
                .taskAssignee(assignee)//只查询该任务负责人的任务
//                .taskId("Activity_0dm0jl0")
                .list();
        for (Task task : list) {
            String cc = (String) runtimeService.getVariable(task.getExecutionId(),"cc");
            System.out.println("cc:" + cc);
            taskService.complete(task.getId());
        }
        return "success";
    }

    public void finalAudit(DelegateExecution execution) {
        String bb = (String) execution.getVariable("bb");
        System.out.println("bb:" + bb);
        String myid = (String) execution.getVariable("myid");
        System.out.println("myid:" + myid);
        execution.setVariable("cc","987654321");
    }
}
