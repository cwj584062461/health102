package com.cwj.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cwj.health.dao.SetmealDao;
import com.cwj.health.entity.PageResult;
import com.cwj.health.entity.QueryPageBean;
import com.cwj.health.pojo.Setmeal;
import com.cwj.health.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);

        //获取id
        Integer setmealId = setmeal.getId();

        //判断获取的检查组id是否为空
        if (null != checkgroupIds){
            //遍历获取的检查组id
            for (Integer checkgroupId : checkgroupIds) {
                //添加套餐和检查组的关系
                setmealDao.addSetmealcheckGroup(setmealId,checkgroupId);
            }
        }

    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        PageResult<Setmeal> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }


}
