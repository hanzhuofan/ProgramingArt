package com.hzf.study.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class GreetingController {

    @PostMapping("/suo/upload")
    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            String path = "D:\\test" ;
            File dest = new File(path + File.separator + fileName);
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @PostMapping("/suo/batch")
    public String batchFileUpload(HttpServletRequest request) {
        List<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFiles("file");
        for (int i = 0; i < fileList.size(); i++) {
            MultipartFile file = fileList.get(i);
            byte[] bytes = new byte[0];
            try {
                bytes = file.getBytes();
                FileUtils.writeByteArrayToFile(new File("D:\\test\\" + file.getOriginalFilename()), bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hello " + name;
    }
}
