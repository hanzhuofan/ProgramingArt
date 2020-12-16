package com.hzf.study.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author zhuofan.han
 * @Date 2020/11/30 10:38
 */
@Component
public class FileUtil {
    public String readString(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),
                StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 写入文件
     */
    public void writeString(File file, String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),
                StandardCharsets.UTF_8))) {
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取json类型的文件
     *
     * @param file
     * @param c
     * @param <T>
     * @return
     */
    public <T> T readJson(File file, Class<T> c) {
        String content = readString(file);
        return JSON.parseObject(content, c);
    }

    /**
     * 写入json类型的文件
     *
     * @param file
     * @param o
     */
    public void writeJson(File file, Object o) {
        String jsonString = JSON.toJSONString(o);
        writeString(file, jsonString);
    }

    public File getFile(String path) {
        int index = path.lastIndexOf(File.separator);
        File dirFile = new File(path.substring(0, index));
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
