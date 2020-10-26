package com.cwj.health.dao;

import com.cwj.health.pojo.CheckGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    void addCheckGroupCheckItem(@Param("checkGroupId")Integer checkGroupId, @Param("checkitemId")Integer checkitemId);

    /**
     * 分页查询
     * @param queryString
     */
    Page<CheckGroup> findPage(String queryString);

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
     */
    void update(CheckGroup checkGroup);


    /**
     * 删除检查组与检查项的关系
     * @param id
     */
    void deleteCheckGroupIdCheckItem(Integer id);

    /**
     * 通过检查组的id统计套餐与检查组关系表的个数
     * @param id
     * @return
     */
    int findSetmealCountByCheckGroupId(int id);

    /**
     * 删除检查组
     * @param id
     */
    void deleteById(int id);
}
