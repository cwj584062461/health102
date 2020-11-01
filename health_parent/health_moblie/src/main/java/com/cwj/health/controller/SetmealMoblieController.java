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
public class SetmealMoblieController {

    @Reference
    private SetmealService setmealService;

    /**
     * 获取套餐列表
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result getSetmeal(){
        List<Setmeal> list = setmealService.getSetmeal();
        //补全路径
        list.forEach(setmeal -> {setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());});
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,list);
    }

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id){
        Setmeal setmeal = setmealService.findDetailById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Setmeal setmeal = setmealService.findById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);
    }
}
