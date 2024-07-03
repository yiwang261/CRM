package com.wang.crm.Service;

import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.DTO.CustomerDTO;
import com.wang.crm.Pojo.Entity.Customer;
import com.wang.crm.Pojo.Query.CustomerQuery;
import com.wang.crm.Result.CustomerExcel;

import java.util.List;

public interface CustomerService {
     int addCustomer(Customer customer) ;

    PageInfo<CustomerDTO> getAllCustomerDTOByPage(CustomerQuery query);

    List<CustomerExcel> exportAllCustomerExcel(List<Integer> ids);

    Customer getCustomerById(Integer id);
}
