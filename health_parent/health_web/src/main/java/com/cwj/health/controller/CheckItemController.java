package com.cwj.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.cwj.health.constant.MessageConstant;
import com.cwj.health.entity.Result;
import com.cwj.health.pojo.CheckItem;
import com.cwj.health.service.CheckItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @GetMapping("/findAll")
    public Result findAll(){
        // 调用服务查询所有的检查项
        List<CheckItem> list = checkItemService.findAll();
        System.out.println(list);
        // 封装返回的结果
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }
}
