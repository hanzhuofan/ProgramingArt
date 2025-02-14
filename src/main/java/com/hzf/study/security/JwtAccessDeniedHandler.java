package com.hzf.study.security;

import com.alibaba.fastjson.JSON;
import com.hzf.study.common.JsonResponseStatus;
import com.hzf.study.common.JsonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhuofan.han
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        JsonResult<String> jsonResult = new JsonResult<>();
        jsonResult.setFail(JsonResponseStatus.NoRight.getCode(), "权限不足，请联系管理员 : " + accessDeniedException.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(jsonResult));
    }
}