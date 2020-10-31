package com.cwj.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.cwj.health.Utils.QiNiuUtils;
import com.cwj.health.constant.MessageConstant;
import com.cwj.health.entity.Result;
import com.cwj.health.pojo.Setmeal;
import com.cwj.health.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    @GetMapping("/getSetmeal")
    public Result getSetmeal() {
        List<Setmeal> list = setmealService.getSetmeal();
        //拼接全路径
        list.forEach(setmeal -> {setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());});
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,list);
    }
}
