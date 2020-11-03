package com.cwj.health.dao;

import com.cwj.health.pojo.User;

public interface UserDao {
    /**
     * 根据用户名称查询用户权限信息
     * @param username
     * @return
     */
    User findByUsername(String username);
}
