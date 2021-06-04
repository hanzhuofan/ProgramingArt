package com.hzf.study.security.mapper;

import com.hzf.study.security.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * The interface Sys user mapper.
 *
 * @author zhuofan.han
 */
public interface SysUserMapper {

    /**
     * Query by username sys user.
     *
     * @param username the username
     * @return the sys user
     */
    SysUser queryByUsername(String username);

    /**
     * Add user integer.
     *
     * @param sysUser the sys user
     * @return the integer
     */
    Integer addUser(@Param("sysUser") SysUser sysUser);

    /**
     * Update user integer.
     *
     * @param sysUser the sys user
     * @return the integer
     */
    Integer updateUser(@Param("sysUser") SysUser sysUser);


    /**
     * Remove user integer.
     *
     * @param sysUser the sys user
     * @return the integer
     */
    Integer removeUser(@Param("sysUser") SysUser sysUser);

}
