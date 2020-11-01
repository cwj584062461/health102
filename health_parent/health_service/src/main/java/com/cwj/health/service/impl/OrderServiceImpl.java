package com.cwj.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cwj.health.constant.MessageConstant;
import com.cwj.health.dao.MemberDao;
import com.cwj.health.dao.OrderDao;
import com.cwj.health.dao.OrdersettingDao;
import com.cwj.health.exception.MyException;
import com.cwj.health.pojo.Member;
import com.cwj.health.pojo.Order;
import com.cwj.health.pojo.OrderSetting;
import com.cwj.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrdersettingDao ordersettingDao;

    @Autowired
    private MemberDao memberDao;

    /**
     * 提交预约
     *
     * @param orderInfo
     * @return
     */
    @Override
    public Order submitOrder(Map<String, String> orderInfo) throws MyException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = null;
        try {
            orderDate = sdf.parse(orderInfo.get("orderDate"));
        } catch (ParseException e) {
            throw new MyException("日期格式不正确，请选择正确的日期");
        }
        //通过日期查询预约设置信息
        OrderSetting orderSetting = ordersettingDao.findByOrderDate(orderDate);
        //判断该日期是否存在
        if (null == orderSetting) {
            //不存在 报错
            throw new MyException("所选日期不能预约，请选择其它日期");
        }
        //判断是否预约满
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            //约满
            throw new MyException("所选日期预约已满，请选择其它日期");
        }
        //判断是否重复预约
        String telephone = orderInfo.get("telephone");
        //通过手机查询会员信息
        Member member = memberDao.findByTelephone(telephone);
        Order order = new Order();
        order.setOrderDate(orderDate);
        order.setSetmealId(Integer.valueOf(orderInfo.get("setmealId")));
        //判断会员是否存在
        if (null != member) {
            //存在
            order.setMemberId(member.getId());
            //查询订单
            List<Order> orderList = orderDao.findByCondition(order);
            //判断是否重复预约
            if (null != orderList) {
                //不为空 则表示重复预约 ，报错
                throw new MyException("该套餐已经预约过了，请勿重复预约");
            }
        }else {
            //不存在 添加会员
            member = new Member();
            member.setName(orderInfo.get("name"));
            member.setSex(orderInfo.get("sex"));
            member.setIdCard(orderInfo.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            member.setPassword("123456");
            member.setRemark("由预约而注册上来的");
            //注册会员
            memberDao.add(member);
            order.setMemberId(member.getId());
        }

        //预约类型
        order.setOrderType(orderInfo.get("orderType"));
        //预约状态
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        //添加预约信息
        orderDao.add(order);

        //更新已预约人数
        int affectedCount = ordersettingDao.editReservationsByOrderDate(orderSetting);
        if (affectedCount==0){
            throw new MyException(MessageConstant.ORDER_FULL);
        }else {
            return order;
        }
    }
}
