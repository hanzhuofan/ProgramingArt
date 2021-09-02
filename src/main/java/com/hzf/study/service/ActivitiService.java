package com.hzf.study.service;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhuofan.han
 * @date 2020/11/13 15:17
 */
@Service
@Slf4j
public class ActivitiService {
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @PostConstruct
    public void init(){
        // 3、使用RepositoryService进行部署
        repositoryService.createDeployment().addClasspathResource("processes/test.bpmn").name("出差申请流程").deploy();
        repositoryService.createDeployment().addClasspathResource("processes/Countersign.bpmn").name("会签业务流程").deploy();
        repositoryService.createDeployment().addClasspathResource("processes/CountersignSequential.bpmn")
            .name("顺序会签业务流程").deploy();
        repositoryService.createDeployment().addClasspathResource("processes/OrSign.bpmn").name("或签业务流程").deploy();
        // 4、输出部署信息
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment1 : list) {
            System.out.println("流程部署id：" + deployment1.getId());
            System.out.println("流程部署名称：" + deployment1.getName());
        }
    }

    public List<ProcessAndBpmnModel> getProcessDefinitionsAndBpmnModel() {
        List<ProcessAndBpmnModel> result = new ArrayList<>();
        for (ProcessDefinition processDefinition : repositoryService.createProcessDefinitionQuery().latestVersion()
            .active().list()) {
            String processDefinitionId = processDefinition.getId();
            System.out.println("流程自定义id：" + processDefinitionId);
            System.out.println("流程自定义名称：" + processDefinition.getName());
            System.out.println("流程自定义key：" + processDefinition.getKey());
            System.out.println("流程自定义Category：" + processDefinition.getCategory());
            System.out.println("流程自定义DeploymentId：" + processDefinition.getDeploymentId());
            System.out.println("流程自定义Version：" + processDefinition.getVersion());
            System.out.println("流程自定义TenantId：" + processDefinition.getTenantId());
            System.out.println("流程自定义DiagramResourceName：" + processDefinition.getDiagramResourceName());
            System.out.println("流程自定义ResourceName：" + processDefinition.getResourceName());
            System.out.println("流程自定义EngineVersion：" + processDefinition.getEngineVersion());
            BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
            if (model != null) {
                Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
                for (FlowElement e : flowElements) {
                    System.out.println("flow element id:" + e.getId() + "  name:" + e.getName() + "   class:"
                        + e.getClass().toString());
                }
            }
            ProcessAndBpmnModel processAndBpmnModel = new ProcessAndBpmnModel();
            processAndBpmnModel.setName(processDefinition.getName());
            processAndBpmnModel.setDescription(processDefinition.getDescription());
            processAndBpmnModel.setKey(processDefinition.getKey());
            processAndBpmnModel.setVersion(processDefinition.getVersion());
            processAndBpmnModel.setCategory(processDefinition.getCategory());
            processAndBpmnModel.setDeploymentId(processDefinition.getDeploymentId());
            processAndBpmnModel.setResourceName(processDefinition.getResourceName());
            processAndBpmnModel.setTenantId(processDefinition.getTenantId());
            processAndBpmnModel.setDiagramResourceName(processDefinition.getDiagramResourceName());
            processAndBpmnModel.setAppVersion(processDefinition.getAppVersion());
            processAndBpmnModel.setEngineVersion(processDefinition.getEngineVersion());
            processAndBpmnModel.setBpmnModel(model);
            result.add(processAndBpmnModel);
        }
        return result;
    }

    public String submitApply(String processDefinitionKey, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        // 输出内容
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());
        System.out.println("Name：" + processInstance.getName());
        System.out.println("LocalizedName：" + processInstance.getLocalizedName());
        System.out.println("ProcessDefinitionName：" + processInstance.getProcessDefinitionName());
        return processInstance.getId();
    }

    public List<TaskDto> getTask(String assignee) {
        TaskQuery active;
        if (StringUtils.isNotBlank(assignee)) {
            active = taskService.createTaskQuery().active().taskCandidateOrAssigned(assignee)
                .taskCandidateGroupIn(Arrays.asList("group1", "group2"));
        } else {
            active = taskService.createTaskQuery().active();
        }
        List<TaskDto> result = new ArrayList<>();
        for (Task task : active.list()) {
            TaskDto taskDto = new TaskDto();
            taskDto.setProcessInstanceId(task.getProcessInstanceId());
            taskDto.setId(task.getId());
            taskDto.setAssignee(task.getAssignee());
            taskDto.setName(task.getName());
            Map<String, Object> variables = new HashMap<>();
            variables.putAll(runtimeService.getVariables(task.getExecutionId()));
            variables.putAll(task.getProcessVariables());
            variables.putAll(task.getTaskLocalVariables());
            taskDto.setVariables(variables);
            result.add(taskDto);
        }
        return result;
    }

    public List<?> getNotify(Map<String,Object> variables) {
        String processInstanceId = (String) variables.get("processInstanceId");
        List<HistoricProcessInstance> hpis = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).list();
        List<HistoricProcessInstanceEntityImpl> result = new LinkedList<>();
        for (HistoricProcessInstance processInstance : hpis) {
            HistoricProcessInstanceEntityImpl executionEntity = (HistoricProcessInstanceEntityImpl)processInstance;
            result.add(executionEntity);
        }
        List<HistoricDetailVariableInstanceUpdateEntityImpl> result2 = new LinkedList<>();
        for (HistoricDetail historicDetail : historyService.createHistoricDetailQuery().processInstanceId(processInstanceId).list()) {
            result2.add((HistoricDetailVariableInstanceUpdateEntityImpl)historicDetail);
        }
        List<HistoricVariableInstanceEntity> result3 = new LinkedList<>();
        for (HistoricVariableInstance historicVariableInstance : historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list()) {
//            if (historicVariableInstance.getTaskId() != null) {
                result3.add((HistoricVariableInstanceEntity)historicVariableInstance);
//            }
        }
//        Map<String, List<HistoricVariableInstanceEntity>> collect = result3.stream().collect(Collectors.groupingBy(HistoricVariableInstanceEntity::getTaskId));
        List<HistoricTaskInstanceEntityImpl> result4 = new LinkedList<>();
        for (HistoricTaskInstance historicTaskInstance : historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list()) {
            HistoricTaskInstanceEntityImpl entity = (HistoricTaskInstanceEntityImpl) historicTaskInstance;
            entity.setQueryVariables(result3);
            result4.add(entity);
        }
        List<HistoricActivityInstanceEntityImpl> result5 = new LinkedList<>();
        for (HistoricActivityInstance historicActivityInstance : historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list()) {
            HistoricActivityInstanceEntityImpl entity = (HistoricActivityInstanceEntityImpl) historicActivityInstance;
            result5.add(entity);
        }
        return result5;
    }

    public void completeApply(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, null, variables);
    }

    public String createApply(String assignee) {
        // 根据流程key 和 任务负责人 查询任务
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("myTest").taskAssignee(assignee).list();
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
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("myTest").taskAssignee(assignee).list();
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProcessAndBpmnModel {
        private String name;
        private String description;
        private String key;
        private int version;
        private String category;
        private String deploymentId;
        private String resourceName;
        private String tenantId = ProcessEngineConfiguration.NO_TENANT_ID;
        private String diagramResourceName;
        private Integer appVersion;
        private String engineVersion;
        private BpmnModel bpmnModel;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TaskDto {
        private String processInstanceId;
        private String id;
        private String assignee;
        private String name;
        private Map<String, Object> variables;
    }
}
