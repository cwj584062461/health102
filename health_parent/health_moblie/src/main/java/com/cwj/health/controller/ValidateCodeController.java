package com.cwj.health.controller;

import com.aliyuncs.exceptions.ClientException;
import com.cwj.health.Utils.SMSUtils;
import com.cwj.health.Utils.ValidateCodeUtils;
import com.cwj.health.constant.MessageConstant;
import com.cwj.health.constant.RedisMessageConstant;
import com.cwj.health.entity.Result;
import com.cwj.health.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    private static final Logger log = LoggerFactory.getLogger(ValidateCodeController.class);

    @Autowired
    private JedisPool jedispool;

    /**
     * 发送体检预约的验证码
     * @param telephone
     * @return
     */
    @PostMapping("/send4Order")
    public Result send4Order(String telephone){
        //从redis取出，如果有值，发送过了。
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        Jedis jedis = jedispool.getResource();
        //取出redis中的验证码
        String codeInRedis = jedis.get(key);
        if (null == codeInRedis){
            //如果没值：
            //生成验证码
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            //调用SMSUtils.发送
            try {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
                log.debug("验证码发送成功 手机{}，验证码{}",telephone,code);
            } catch (ClientException e) {
               log.error("发送验证码失败");
            }
            //存入redis，加入有效时间，过期失效
            jedis.setex(key,15*60,code);
            return new Result(false, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }else {
            //有值，则返回提示
           return new Result(false,"验证码已经发送过了，请注意查收");
        }
    }

    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
        //从redis取出，如果有值，发送过了。
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        Jedis jedis = jedispool.getResource();
        //取出redis中的验证码
        String codeInRedis = jedis.get(key);
        if (null == codeInRedis){
            //如果没值：
            //生成验证码
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            //调用SMSUtils.发送
            try {
                //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
                log.debug("验证码发送成功 手机{}，验证码{}",telephone,code);
            } catch (Exception e) {
                log.error("发送验证码失败",e);
            }
            //存入redis，加入有效时间，过期失效
            jedis.setex(key,10*60,code);
            return new Result(false, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }else {
            //有值，则返回提示
            return new Result(false,"验证码已经发送过了，请注意查收");
        }
    }

}
