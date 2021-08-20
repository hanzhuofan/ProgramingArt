package com.hzf.study.controller;

import com.hzf.study.service.GreetingService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zhuofan.han
 * @date 2021/8/19 11:17
 */
@RestController
@RequestMapping("/hello")
public class GreetingController {
    @Autowired
    private GreetingService greetingService;

    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public GreetingService getGreetingService() {
        return greetingService;
    }

    @PostMapping("/suo/upload")
    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            String path = "D:\\test";
            File dest = new File(path + File.separator + fileName);
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @PostMapping("/suo/batch")
    public String batchFileUpload(HttpServletRequest request) {
        //解析request对象，并得到一个表单项的集合
        List<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFiles("file");
        for (MultipartFile file : fileList) {
            try {
                byte[] bytes = file.getBytes();
                FileUtils.writeByteArrayToFile(new File("D:\\test\\" + file.getOriginalFilename()), bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }

    @GetMapping("")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hello " + name;
    }

    @GetMapping("/activiti")
    public String activiti() {
        return greetingService.activiti();
    }
}
