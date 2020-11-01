package com.cwj.health.service;

import com.cwj.health.exception.MyException;
import com.cwj.health.pojo.Order;

import java.util.Map;

public interface OrderService {
    /**
     * 提交 预约
     * @param orderInfo
     * @return
     */
    Order submitOrder(Map<String, String> orderInfo) throws MyException;

    /**
     * 查询套餐信息
     * @param id
     * @return
     */
    Map<String, Object> findById(int id);
}
