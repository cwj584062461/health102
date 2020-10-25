package com.cwj.health.dao;

import com.cwj.health.pojo.CheckGroup;
import com.github.pagehelper.Page;

public interface CheckgroupDao {
    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组与检查项的关系
     * @param checkGroupId
     * @param checkitemId
     */
    void addCheckGroupCheckItem(Integer checkGroupId, Integer checkitemId);

    /**
     * 分页查询
     * @param queryString
     */
    Page<CheckGroup> findPage(String queryString);

}
