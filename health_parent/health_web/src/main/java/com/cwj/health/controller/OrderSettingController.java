package com.cwj.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cwj.health.Utils.POIUtils;
import com.cwj.health.constant.MessageConstant;
import com.cwj.health.entity.Result;
import com.cwj.health.pojo.OrderSetting;
import com.cwj.health.service.OrdersettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    @Reference
    private OrdersettingService ordersettingService;

    /**
     * 导入预约设置信息
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            //解析excel
            List<String[]> strings = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettingsList = new ArrayList<>();
            //日期格式解析
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            Date orderDate = null;
            OrderSetting os = null;
            //遍历数据
            for (String[] dataArr : strings) {
                orderDate = sdf.parse(dataArr[0]);
                Integer number = Integer.valueOf(dataArr[1]);
                os = new OrderSetting(orderDate,number);
                orderSettingsList.add(os);
            }
            ordersettingService.add(orderSettingsList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            log.error("导入预约设置失败",e);
        }
        return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
    }

    /**
     * 通过月份查询预约设置信息
     * @param month
     * @return
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        List<Map<String,Integer>> data = ordersettingService.getOrderSettingByMonth(month);
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,data);
    }
}
