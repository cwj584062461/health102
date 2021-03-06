package com.cwj.health.service;

import com.cwj.health.entity.PageResult;
import com.cwj.health.entity.QueryPageBean;
import com.cwj.health.exception.MyException;
import com.cwj.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    /**
     * 查询所有的检查项
     * @return
     */
    List<CheckItem> findAll();


    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 删除检查项
     * @param id
     */
    void deleteById(int id) throws MyException;

    /**
     * 通过id查询检查项
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 修改检查项
     * @param checkItem
     */
    void update(CheckItem checkItem);
}
