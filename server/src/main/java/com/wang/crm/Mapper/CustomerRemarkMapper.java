package com.wang.crm.Mapper;

import com.wang.crm.Pojo.Entity.CustomerRemark;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_customer_remark(客户跟踪记录表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.CustomerRemark
*/
public interface CustomerRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerRemark record);

    int insertSelective(CustomerRemark record);

    CustomerRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerRemark record);

    int updateByPrimaryKey(CustomerRemark record);

    List<CustomerRemark> getCustomerRemarkPage(Integer customerId);
}
