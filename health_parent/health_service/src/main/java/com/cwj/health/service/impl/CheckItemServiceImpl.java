package com.cwj.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cwj.health.dao.CheckItemDao;
import com.cwj.health.pojo.CheckItem;
import com.cwj.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;


    /**
     * 查询所有的检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
