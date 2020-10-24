package com.cwj.health.dao;

import com.cwj.health.entity.QueryPageBean;
import com.cwj.health.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface CheckItemDao {
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
     * @param queryString
     * @return
     */
    Page<CheckItem> findPage(String queryString);

    /**
     * 删除检查项
     * @param id
     */
    void deleteById(int id);

    /**
     *检查 检查项是否被检查组使用了
     * @param id
     * @return
     */
    int findCountByCheckItemId(int id);
}
