package com.hzf.study.utils;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @author changzhu.wu
 * @date 2018/11/23 13:26
 */
public class RequestHelper {

    private static Set<String> filteredKeys;
    static {
        filteredKeys = new LinkedHashSet<String>()
        {
            {
                add("longitude");
                add("latitude");
            }
        };
    }

    private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object object, String name, Object value) {
            if((value instanceof Double || value instanceof BigDecimal || value instanceof Float) && !filteredKeys.contains(name)){
                return new BigDecimal(value.toString()).setScale(1, BigDecimal.ROUND_HALF_UP);
            }
            return value;
        }
    };

    public static ResponseEntity<?> formatError(int errCode,Object errMsg) {
        Map<String, Object> rootmap = new LinkedHashMap<>();
        rootmap.put("errCode",errCode);
        if ( errMsg == null ) {
            errMsg = new Object();
        }
        rootmap.put("errMsg",errMsg);
        String body = JSON.toJSONString(rootmap,SerializerFeature.WriteMapNullValue);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public static ResponseEntity<?> formatSuccess() {
        return formatError(0,"Success");
    }

    public static ResponseEntity<?> formatSuccessResult(Object data) {
        Map<String, Object> rootmap = new LinkedHashMap<>();
        rootmap.put("errCode",0);
        rootmap.put("errMsg","Success");
        if ( data == null ) {
            data = new Object();
        }
        rootmap.put("data", data);
        String body = JSON.toJSONString(rootmap, filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
