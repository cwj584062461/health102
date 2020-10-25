package com.cwj.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cwj.health.dao.CheckgroupDao;
import com.cwj.health.entity.PageResult;
import com.cwj.health.entity.QueryPageBean;
import com.cwj.health.pojo.CheckGroup;
import com.cwj.health.service.CheckgroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;


@Service(interfaceClass = CheckgroupService.class)
public class CheckgroupServiceImpl implements CheckgroupService {

    @Autowired
    private CheckgroupDao checkgroupDao;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkgroupDao.add(checkGroup);

        //获取新增的检查组的id
        Integer checkGroupId = checkGroup.getId();

        //判断选中的检查项id是否为空
        if (null!=checkitemIds){
            //遍历选中的检查项id
            for (Integer checkitemId : checkitemIds) {
                    //添加检查组与检查项的关系
                checkgroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }

    }


    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断查询条件是否为空
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())){
            //不为空 则拼接%% 使语句成为模糊查询语句
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }

        //调用dao层
        Page<CheckGroup> page = checkgroupDao.findPage(queryPageBean.getQueryString());

        //将结果封装进PageResult
        PageResult<CheckGroup> pageResult = new PageResult<CheckGroup>(page.getTotal(),page.getResult());

        return pageResult;
    }
}