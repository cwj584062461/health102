package com.cwj.health.service;

import com.cwj.health.pojo.Permission;
import com.cwj.health.pojo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //假设从数据库查询用户
        com.cwj.health.pojo.User userInDb = findByUsername(username);
        //判断用户是否存在
        if (null != userInDb) {
            //存在 授权
            ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            //获取用户的角色集合
            Set<Role> roles = userInDb.getRoles();
            if (null != roles) {
                GrantedAuthority sga = null;
                //遍历用户的角色
                for (Role role : roles) {
                    sga = new SimpleGrantedAuthority(role.getKeyword());
                    //授予角色
                    authorities.add(sga);
                    //获取角色对应的权限
                    Set<Permission> permissions = role.getPermissions();
                    if (null != permissions) {
                        //遍历权限
                        for (Permission permission : permissions) {
                            sga = new SimpleGrantedAuthority(permission.getKeyword());
                            //授予权限
                            authorities.add(sga);
                        }
                    }

                }
            }

            User securityUser = new User(username, userInDb.getPassword(), authorities);
            return securityUser;

        }
        return null;
    }


    public com.cwj.health.pojo.User findByUsername(String username) {
        if ("admin".equals(username)) {
            com.cwj.health.pojo.User user = new com.cwj.health.pojo.User();
            user.setUsername("admin");

            //使用密文
            user.setPassword("$2a$10$IfPkaV5WRkaaoDODWPLU9uxQgt3qzfVUj1PxnzNPyiY.C7ycQRvAm");

            //角色
            Role role = new Role();
            role.setKeyword("ROLE_ADMIN");

            //权限
            Permission permission = new Permission();
            permission.setKeyword("ADD_CHECKITEM");

            //给角色添加权限
            role.getPermissions().add(permission);

            //把角色放进集合中
            HashSet<Role> roles = new HashSet<>();
            roles.add(role);

            role = new Role();
            role.setKeyword("ABC");
            roles.add(role);

            //给用户添加角色
            user.setRoles(roles);
            return user;
        }
        return null;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches("1234", "$2a$10$C2I8PHWnBtqMJlvKD7DsCuP9Kl4uQT4TIqBTgda1y/Pekp6Tb/4GO"));
        System.out.println(encoder.matches("1234", "$2a$10$IfPkaV5WRkaaoDODWPLU9uxQgt3qzfVUj1PxnzNPyiY.C7ycQRvAm"));
        System.out.println(encoder.matches("1234", "$2a$10$u/BcsUUqZNWUxdmDhbnoeeobJy6IBsL1Gn/S0dMxI2RbSgnMKJ.4a"));
    }
}
