package com.cwj.health.dao;

import com.cwj.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    /**
     * 查询所有的检查项
     * @return
     */
    List<CheckItem> findAll();

}
