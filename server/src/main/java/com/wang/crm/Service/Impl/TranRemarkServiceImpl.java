package com.wang.crm.Service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.crm.Constant.Constants;
import com.wang.crm.Mapper.TranRemarkMapper;
import com.wang.crm.Pojo.Entity.TranRemark;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.TranRemarkQuery;
import com.wang.crm.Service.TranRemarkService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TranRemarkServiceImpl implements TranRemarkService {

    @Resource
    private TranRemarkMapper tranRemarkMapper;

    /**
     * 提交交易的跟踪记录
     *
     * @param tranRemarkQuery
     * @return
     */
    @Override
    public int saveTranRemark(TranRemarkQuery tranRemarkQuery) {
        TranRemark tranRemark = new TranRemark();

        BeanUtils.copyProperties(tranRemarkQuery, tranRemark);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        tranRemark.setCreateTime(new Date()); //创建时间
        tranRemark.setCreateBy(user.getId()); //创建人id

        return tranRemarkMapper.insertSelective(tranRemark);
    }

    /**
     * 分页查询交易跟踪记录
     *
     * @param current
     * @param tranId
     * @return
     */
    @Override
    public PageInfo<TranRemark> getTranRemarkByPage(Integer current, Integer tranId) {
        //1.设置PageHelper
//        PageHelper.startPage(current, Constants.DEFAULT_PAGE_SIZE);
        //2.查询
        List<TranRemark> list = tranRemarkMapper.selectTranRemarkPage(tranId);
        //3.封装分页数据到PageInfo
        PageInfo<TranRemark> info = new PageInfo<>(list);

        return info;
    }
}
