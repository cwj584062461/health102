package com.cwj.health.service;

import com.cwj.health.entity.PageResult;
import com.cwj.health.entity.QueryPageBean;
import com.cwj.health.exception.MyException;
import com.cwj.health.pojo.CheckGroup;

import java.util.List;

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

    /**
     * 通过Id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过检查组checkgroupId查检查项CheckItemIds
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 修改检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 删除检查组
     * @param id
     */
    void deleteById(int id) throws MyException;
}
