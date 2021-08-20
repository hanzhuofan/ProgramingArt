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
    SysUser queryByUsername(@Param("username") String username);

    /**
     * Add user integer.
     *
     * @param sysUser the sys user
     * @return the integer
     */
    Integer addUser(SysUser sysUser);

    /**
     * Update user integer.
     *
     * @param sysUser the sys user
     * @return the integer
     */
    Integer updateUser(SysUser sysUser);


    /**
     * Remove user integer.
     *
     * @param sysUser the sys user
     * @return the integer
     */
    Integer removeUser(SysUser sysUser);

}
