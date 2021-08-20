package com.hzf.study.security;

import com.alibaba.fastjson.JSON;
import com.hzf.study.common.Constants;
import com.hzf.study.common.JsonResult;
import com.hzf.study.security.entity.JwtUserDetails;
import com.hzf.study.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhuofan.han
 */
@Component
public class JwtLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        String json = JSON.toJSONString(jwtUserDetails);
        String jwtToken = JwtUtils.createJwtToken(json, Constants.DEFAULT_TOKEN_TIME_MS);
        //签发token
        JsonResult<String> jsonResult = new JsonResult<>();
        jsonResult.setSuccess(jwtToken);
        response.getWriter().write(JSON.toJSONString(jsonResult));
    }
}