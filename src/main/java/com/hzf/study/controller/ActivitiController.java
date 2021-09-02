package com.hzf.study.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hzf.study.service.ActivitiService;
import com.hzf.study.utils.Result;

/**
 * @author zhuofan.han
 * @date 2021/8/19 11:17
 */
@RestController
@RequestMapping("/activiti")
public class ActivitiController {
    @Autowired
    private ActivitiService activitiService;

    @GetMapping("/process")
    public Result<?> getProcessDefinitionsAndBpmnModel() {
        return Result.success(activitiService.getProcessDefinitionsAndBpmnModel());
    }

    @PostMapping("/submit")
    public Result<?> submitApply(@RequestParam String processDefinitionKey,
        @RequestBody Map<String, Object> variables) {
        return Result.success(activitiService.submitApply(processDefinitionKey, variables));
    }

    @GetMapping("/task")
    public Result<?> getTask(@RequestParam String assignee) {
        return Result.success(activitiService.getTask(assignee));
    }

    @PostMapping("/complete")
    public Result<?> completeApply(@RequestParam String taskId, @RequestBody Map<String,Object> variables){
        activitiService.completeApply(taskId, variables);
        return Result.success();
    }

    @PostMapping("/notify")
    public Result<?> getNotify(@RequestBody Map<String,Object> variables) {
        return Result.success(activitiService.getNotify(variables));
    }

    @RequestMapping("/create")
    public String createApply(String assignee){
        return activitiService.createApply(assignee);
    }

    @RequestMapping("/secondAudit")
    public String secondAudit(String assignee){
        return activitiService.secondAudit(assignee);
    }
}
