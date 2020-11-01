package com.cwj.health.dao;

import com.cwj.health.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {
    /**
     * 添加套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐和检查组的关系
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealcheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findPage(String queryString);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 根据套餐id查询选中的检查组id
     * @param id
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(int id);

    /**
     * 修改套餐
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 删除套餐与检查组的关系
     * @param id
     */
    void deleteSetmealCheckGroup(Integer id);

    /**
     * 删除套餐
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询套餐是否被订单使用
     * @param id
     * @return
     */
    int findOrderCountBySetmealId(int id);

    /**
     * 找图片名
     * @return
     */
    List<String> findImgs();

    /**
     * 获取套餐列表
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    Setmeal findDetailById(int id);
}
