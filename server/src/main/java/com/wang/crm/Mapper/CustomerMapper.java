package com.wang.crm.Mapper;

import com.wang.crm.Com.DataScope;
import com.wang.crm.Pojo.DTO.CustomerDTO;
import com.wang.crm.Pojo.Entity.Customer;
import com.wang.crm.Pojo.Query.CustomerQuery;
import com.wang.crm.Result.CustomerExcel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_customer(客户表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.Customer
*/
@Mapper
public interface CustomerMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    @DataScope(tableAlias = "t_customer", tableField = "create_by")
    List<CustomerDTO> getCustomerDTOByPage(CustomerQuery query);

//    List<CustomerDTO> getCustomerExcelByIds(List<Integer> ids);

    @DataScope(tableAlias = "owner", tableField = "id")
    List<CustomerExcel> getAllOrByIdsCustomerExcel(CustomerQuery query);

    Integer getCustomerCount();
}
