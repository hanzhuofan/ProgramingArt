package com.hzf.study.security.entity;

import lombok.Data;

/**
 * @author zhuofan.han
 */
@Data
public class SysUser {
    private Integer userId;
    private String username;
    private String encodePassword;
    private Integer age;
}
