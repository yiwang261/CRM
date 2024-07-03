package com.wang.crm.Service;


import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.Entity.CustomerRemark;
import com.wang.crm.Pojo.Query.CustomerRemarkQuery;

/**
 * @author yiwan
 */
public interface CustomerRemarkService {
    PageInfo<CustomerRemark> getCustomerRemarkByPage(Integer current, Integer customerId);

    int saveCustomerRemark(CustomerRemarkQuery customerRemarkQuery);
}
