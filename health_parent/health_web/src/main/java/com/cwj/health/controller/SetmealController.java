package com.cwj.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cwj.health.Utils.QiNiuUtils;
import com.cwj.health.constant.MessageConstant;
import com.cwj.health.entity.PageResult;
import com.cwj.health.entity.QueryPageBean;
import com.cwj.health.entity.Result;
import com.cwj.health.pojo.Setmeal;
import com.cwj.health.service.SetmealService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    /**
     * 套餐上传图片
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        //获取图片名称
        String originalFilename = imgFile.getOriginalFilename();
        //获取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //生成文件名
        String filename = UUID.randomUUID() + suffix;

        //调用七牛云
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),filename);

            //返回数据给页面
            Map<String, String> map = new HashMap<>();
            map.put("imgName",filename);
            map.put("domain",QiNiuUtils.DOMAIN);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
    }

    /**
     * 添加套餐
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        setmealService.add(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> pageResult= setmealService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }
}
