package com.cwj.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.cwj.health.constant.MessageConstant;
import com.cwj.health.entity.PageResult;
import com.cwj.health.entity.QueryPageBean;
import com.cwj.health.entity.Result;
import com.cwj.health.pojo.CheckItem;
import com.cwj.health.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有的检查项
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        // 调用服务查询所有的检查项
        List<CheckItem> list = checkItemService.findAll();
        System.out.println(list);
        // 封装返回的结果
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /**
     * 新增检查项
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){

        checkItemService.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询
     * @return
     */
    @PostMapping("/findPage")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        checkItemService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 通过id查询检查项
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    /**
     * 修改检查项
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        checkItemService.update(checkItem);
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}
