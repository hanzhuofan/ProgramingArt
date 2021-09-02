package com.hzf.study.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.alibaba.fastjson.JSONPath;

import lombok.extern.slf4j.Slf4j;

/**
 * @author changzhu.wu
 * @date 2018/11/28 8:39
 */
@Slf4j
public class JSONHelper {

    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null){
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static String parseString(Object rootObject, String path)
    {
        String ret = null;
        try
        {
            if (JSONPath.contains(rootObject, path))
            {
                ret = JSONPath.eval(rootObject,path).toString().trim();
            }
        }
        catch (Exception ex){

        }
        return  ret;
    }

    public static Integer parseInt(Object rootObject, String path)
    {
        Integer ret = null;
        try
        {
            if (JSONPath.contains(rootObject, path))
            {
                ret = Integer.parseInt(JSONPath.eval(rootObject,path).toString().trim());
            }
        }
        catch (Exception ex){

        }
        return  ret;
    }
}
