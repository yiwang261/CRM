package com.wang.crm.Service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.crm.Constant.Constants;
import com.wang.crm.Mapper.CustomerRemarkMapper;
import com.wang.crm.Pojo.Entity.CustomerRemark;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.CustomerRemarkQuery;
import com.wang.crm.Service.CustomerRemarkService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerRemarkServiceImpl implements CustomerRemarkService {

    @Resource
    private CustomerRemarkMapper customerRemarkMapper;

    @Override
    public PageInfo<CustomerRemark> getCustomerRemarkByPage(Integer current, Integer customerId) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.DEFAULT_PAGE_SIZE);
        //2.查询
        List<CustomerRemark> list = customerRemarkMapper.getCustomerRemarkPage(customerId);
        //3.封装分页数据到PageInfo
        PageInfo<CustomerRemark> info = new PageInfo<>(list);

        return info;
    }

    @Override
    public int saveCustomerRemark(CustomerRemarkQuery customerRemarkQuery) {
        CustomerRemark tCustomerRemark = new CustomerRemark();


        BeanUtils.copyProperties(customerRemarkQuery, tCustomerRemark);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//创建时间
        tCustomerRemark.setCreateTime(new Date());
        //创建人id
        tCustomerRemark.setCreateBy(user.getId());

        return customerRemarkMapper.insertSelective(tCustomerRemark);
    }
}
