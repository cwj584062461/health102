package com.cwj.health.controller;

import com.cwj.health.constant.MessageConstant;
import com.cwj.health.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUsername")
    public Result getUsername(){
        //获取用户的认证信息
        User loginUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        //获取用户名
        String username = loginUser.getUsername();

        return new Result(true, MessageConstant.GET_MENU_SUCCESS,username);
    }
}
