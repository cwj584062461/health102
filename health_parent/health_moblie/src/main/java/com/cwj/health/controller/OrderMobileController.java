package com.cwj.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cwj.health.constant.MessageConstant;
import com.cwj.health.constant.RedisMessageConstant;
import com.cwj.health.entity.Result;
import com.cwj.health.pojo.Order;
import com.cwj.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderMobileController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 提交 预约
     *
     * @param orderInfo
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> orderInfo) {
        //验证码校验
        Jedis jedis = jedisPool.getResource();
        //通过手机号码获取redis中的验证码
        String telephone = orderInfo.get("telephone");
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        String codeInRedis = jedis.get(key);
        if (StringUtils.isEmpty(codeInRedis)) {
            //没有值
            //返回 重新获取验证码
            return new Result(false, "请重新获取验证码");
        }
        //有值
        //获取前端传过来的验证码
        String validateCode = orderInfo.get("validateCode");
        //比较redis中的验证码与前端的验证是否相同
        if (!codeInRedis.equals(validateCode)) {
            //不同时，返回验证码不正确
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //相同，删除key，
        jedis.del(key);
        // 设置预约类型（health_mobile, 写死为微信预约）
        orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
        // 调用服务，返回结果给页面
        Order order = orderService.submitOrder(orderInfo);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    @GetMapping("/findById")
    public Result findById(int id){
        //调用服务
        Map<String, Object> orderInfo = orderService.findById(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,orderInfo);
    }
}
