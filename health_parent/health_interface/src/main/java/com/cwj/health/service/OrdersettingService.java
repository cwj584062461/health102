package com.cwj.health.service;

import com.cwj.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrdersettingService {
    /**
     * 批量导入预约设置
     * @param orderSettingsList
     */
    void add(List<OrderSetting> orderSettingsList);

    /**
     * 通过月份查询预约设置信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);
}
