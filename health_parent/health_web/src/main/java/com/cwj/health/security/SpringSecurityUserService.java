package com.cwj.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cwj.health.pojo.Permission;
import com.cwj.health.pojo.Role;
import com.cwj.health.pojo.User;
import com.cwj.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    /**
     * 提供登陆用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询会员
        User user = userService.findByUsername(username);
        //判断会员是否存在
        if (null!=user){
            //存在
            //获取密码
            String password = user.getPassword();
            //权限集合
            ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            //获取用户用户的角色
            SimpleGrantedAuthority sga = null;
            Set<Role> roles = user.getRoles();
            //判断角色集合是否为空
            if (null != roles){
                //存在，遍历角色集合
                for (Role role : roles) {
                    sga = new SimpleGrantedAuthority(role.getKeyword());
                    //授予角色
                    authorities.add(sga);
                    //获取角色的权限
                    Set<Permission> permissions = role.getPermissions();
                    //判断权限是否为空
                    if (null!= permissions){
                        //不为空，遍历权限集合
                        for (Permission permission : permissions) {
                            sga = new SimpleGrantedAuthority(permission.getKeyword());
                            //授予权限
                            authorities.add(sga);
                        }
                    }
                }
            }

            return  new org.springframework.security.core.userdetails.User(username,password,authorities);
        }
        return null;
    }
}
