package com.hzf.study.security.filter;

import com.hzf.study.security.entity.JwtLoginToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhuofan.han
 * @date 2021/8/19
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * 拦截逻辑
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        //创建未认证的凭证(etAuthenticated(false)),注意此时凭证中的主体principal为用户名
        JwtLoginToken jwtLoginToken = new JwtLoginToken(userName, password);
        //将认证详情(ip,sessionId)写到凭证
        jwtLoginToken.setDetails(new WebAuthenticationDetails(request));
        //AuthenticationManager获取受支持的AuthenticationProvider(这里也就是JwtAuthenticationProvider),
        //生成已认证的凭证,此时凭证中的主体为userDetails  --- 这里会委托给AuthenticationProvider实现类来验证
        // 即 跳转到 JwtAuthenticationProvider.authenticate 方法中认证
        return this.getAuthenticationManager().authenticate(jwtLoginToken);
    }
}
