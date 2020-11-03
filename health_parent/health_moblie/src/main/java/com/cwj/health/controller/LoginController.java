package com.cwj.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cwj.health.constant.MessageConstant;
import com.cwj.health.constant.RedisMessageConstant;
import com.cwj.health.entity.Result;
import com.cwj.health.pojo.Member;
import com.cwj.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @PostMapping("/check")
    public Result checkMember(@RequestBody Map<String, String> loginInfo, HttpServletResponse response) {
        //获取手机号
        String telephone = loginInfo.get("telephone");
        //获取前端传来的验证码
        String validateCode = loginInfo.get("validateCode");

        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        Jedis jedis = jedisPool.getResource();
        //获取redis中的验证码
        String codeInRedis = jedis.get(key);
        //判断redis中是否存在验证码
        if (null == codeInRedis) {
            //不存在，给提示
            return new Result(false, "请重新获取验证码");
        }
        //存在 ，对比两个验证码是否相同
        if (!codeInRedis.equals(validateCode)) {
            //不同 则报错
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //清除验证码
        jedis.del(key);
        //根据手机查询查询用户
        Member member = memberService.findByTelephone(telephone);
        //判断用户是否存在
        if (null == member) {
            //不存在 完成会员注册
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            member.setRemark("手机快速注册");
            //添加会员
            memberService.add(member);
        }
        //将手机号添加进cookie中
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        //cookie最大有效时间 1个月
        cookie.setMaxAge(30 * 24 * 60 * 60);
        //访问路径
        cookie.setPath("/");
        response.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }

}
