package com.hzf.study.security.service.impl;

import com.hzf.study.security.entity.JwtUserDetails;
import com.hzf.study.security.entity.SysUser;
import com.hzf.study.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Objects;

/**
 * 代理 {@link org.springframework.security.provisioning.UserDetailsManager} 所有功能
 *
 * @author zhuofan.han
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    /**
     * Create user.
     *
     * @param user the user
     */
    public void createUser(UserDetails user) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(user.getUsername());
        sysUser.setEncodePassword(user.getPassword());
        sysUser.setRoles(user.getAuthorities().toString());

        sysUserService.addUser(sysUser);
    }


    /**
     * Update user.
     *
     * @param user the user
     */
    public void updateUser(UserDetails user) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(user.getUsername());
        sysUser.setEncodePassword(user.getPassword());
        sysUser.setRoles(user.getAuthorities().toString());

        sysUserService.updateUser(sysUser);
    }


    /**
     * Delete user.
     *
     * @param username the username
     */
    public void deleteUser(String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);

        sysUserService.removeUser(sysUser);
    }

    /**
     * Change password.
     *
     * @param oldPassword the old password
     * @param newPassword the new password
     */
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext()
                .getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException(
                    "Can't change password as no Authentication object found in context "
                            + "for current user.");
        }

        String username = currentUser.getName();

        UserDetails user = this.loadUserByUsername(username);


        if (user == null) {
            throw new IllegalStateException("Current user doesn't exist in database.");
        }

        // 实现具体的更新密码逻辑

        if (!Objects.equals(oldPassword, user.getPassword())) {
            throw new IllegalStateException("Current password doesn't  match the password provided");
        }

        SysUser sysUser = new SysUser();
        sysUser.setUsername(user.getUsername());
        sysUser.setEncodePassword(newPassword);

        sysUserService.updateUser(sysUser);

    }


    /**
     * User exists boolean.
     *
     * @param username the username
     * @return the boolean
     */
    public boolean userExists(String username) {
        return Objects.nonNull(this.sysUserService.queryByUsername(username));
    }


    /**
     * Load user by username user details.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.queryByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username: " + username + " notfound");
        }
        return JwtUserDetails.of(user);
    }

}
