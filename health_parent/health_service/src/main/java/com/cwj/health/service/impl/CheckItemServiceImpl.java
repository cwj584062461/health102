package com.cwj.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cwj.health.constant.MessageConstant;
import com.cwj.health.dao.CheckItemDao;
import com.cwj.health.entity.PageResult;
import com.cwj.health.entity.QueryPageBean;
import com.cwj.health.exception.MyException;
import com.cwj.health.pojo.CheckItem;
import com.cwj.health.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;


    /**
     * 查询所有的检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
     * 新增检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //使用分页助手
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        //判断是否有查询条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())){
            //有着拼接%%  成模糊查询语句
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }

        //调用dao层
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean.getQueryString());

        //将结果封装导pageResult
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 删除检查项
     * @param id
     */
    @Override

    public void deleteById(int id)throws MyException {
        //判断这个检查项是否被使用
        int cnt = checkItemDao.findCountByCheckItemId(id);
        if (cnt>0){
            //如果被使用了，则报错
            throw new MyException("该检查项已经被使用了，不能删除!");
        }
        //没有被使用，则删除
        checkItemDao.deleteById(id);
     }

    /**
     * 通过id查询检查项
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);

    }

    /**
     * 修改检查项
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }


}
