package com.cwj.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cwj.health.dao.OrdersettingDao;
import com.cwj.health.exception.MyException;
import com.cwj.health.pojo.OrderSetting;
import com.cwj.health.service.OrdersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrdersettingService.class)
public class OrdersettingServiceImpl implements OrdersettingService {

    @Autowired
    private OrdersettingDao ordersettingDao;

    /**
     * 批量导入预约设置
     *
     * @param orderSettingsList
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingsList) {
        //遍历
        for (OrderSetting orderSetting : orderSettingsList) {
            //通过日期查询预约信息
            OrderSetting osInDb = ordersettingDao.findByOrderDate(orderSetting.getOrderDate());
            //判断预约信息是否为空
            if (null != osInDb) {
                //存在
                //判断更新后的最大预约数是否大于已预约数
                if (orderSetting.getNumber() < osInDb.getNumber()) {
                    //小于，报错
                    throw new MyException("最大预约数不能小于已已预约数");
                }

                //更新最大预约数
                ordersettingDao.updateNumber(orderSetting);
            }else {
                //不存在
                //添加预约信息
                ordersettingDao.add(orderSetting);
            }
        }
    }

    /**
     * 通过月份查询预约设置信息
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        month+="%";
        return ordersettingDao.getOrderSettingByMonth(month);
    }

    /**
     * 基于日历预约设置
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //通过日历查询预约设置信息
        OrderSetting osInDb = ordersettingDao.findByOrderDate(orderSetting.getOrderDate());
        //判断查询结果是否为空
        if (null != osInDb){
            //有预约设置信息
            //判断最大预约数是否大于已预约数
            if (orderSetting.getNumber() < osInDb.getNumber()){
                //小于 则报错
                throw new MyException("最大预约数不能小于已已预约数");
            }

            //更新最大可预约数
            ordersettingDao.updateNumber(orderSetting);
        }else {
            //不存在则添加预约设置信息
            ordersettingDao.add(orderSetting);
        }
    }
}
