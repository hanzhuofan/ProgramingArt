package com.hzf.study.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhuofan.han
 */
public class JwtUserDetails implements UserDetails {
    private Integer id;

    private String userId;

    private String username;

    private String password;

    private List<Role> roles;

    public static JwtUserDetails of(SysUser user) {
        JwtUserDetails localUserDetail = new JwtUserDetails();
        localUserDetail.setId(user.getId());
        localUserDetail.setUsername(user.getUsername());
        localUserDetail.setPassword(user.getEncodePassword());
        return localUserDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String userPwd) {
        this.password = userPwd;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles == null ? new ArrayList<SimpleGrantedAuthority>() : roles.stream().map(r ->new SimpleGrantedAuthority(r.getRoleId())).collect(Collectors.toList());
    }

    @Override

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}