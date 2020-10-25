package com.cwj.health.service;

import com.cwj.health.entity.PageResult;
import com.cwj.health.entity.QueryPageBean;
import com.cwj.health.pojo.CheckGroup;

public interface CheckgroupService {

    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);


    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);
}
