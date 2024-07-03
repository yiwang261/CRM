package com.wang.crm.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.crm.Constant.Constants;
import com.wang.crm.Mapper.ClueMapper;
import com.wang.crm.Mapper.CustomerMapper;
import com.wang.crm.Pojo.DTO.CustomerDTO;
import com.wang.crm.Pojo.Entity.Clue;
import com.wang.crm.Pojo.Entity.Customer;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.CustomerQuery;
import com.wang.crm.Result.CustomerExcel;
import com.wang.crm.Service.CustomerService;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private ClueMapper clueMapper;

    @Transactional
    @Override
    public int addCustomer(Customer customer) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        customer.setCreateBy(user.getId());
        customer.setCreateTime(new Date());

        Clue clue = new Clue();
        clue.setEditTime(new Date());
        clue.setEditBy(user.getId());
        clue.setId(customer.getClueId());
        clue.setState(-1);
        clueMapper.updateByPrimaryKeySelective(clue);
        return customerMapper.insert(customer);
    }

    @Override
    public PageInfo<CustomerDTO> getAllCustomerDTOByPage(CustomerQuery query) {
        PageHelper.startPage(query.getCurrent(), query.getSize());
        List<CustomerDTO> list =customerMapper.getCustomerDTOByPage(query);
        PageInfo<CustomerDTO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<CustomerExcel> exportAllCustomerExcel(List<Integer> ids) {

//        List<CustomerDTO> customerDTOS = customerMapper.getCustomerExcelByIds(ids);
//        customerDTOS.forEach(customerDTO -> {
//            CustomerExcel excel = new CustomerExcel();
//            excel.setOwnerName(ObjectUtils.isEmpty(customerDTO.getClueDTO().getOwner()) ? Constants.EMPTY : customerDTO.getClueDTO().getOwner().getName());
//            excel.setActivityName(ObjectUtils.isEmpty(customerDTO.getClueDTO().getActivity().getName()) ? Constants.EMPTY : customerDTO.getClueDTO().getActivity().getName());
//            excel.setFullName(customerDTO.getClueDTO().getClue().getFullName());
//            excel.setAppellationName(ObjectUtils.isEmpty(customerDTO.getClueDTO().getAppellation().getTypeValue()) ? Constants.EMPTY : customerDTO.getClueDTO().getAppellation().getTypeValue());
//            excel.setPhone(customerDTO.getClueDTO().getClue().getPhone());
//            excel.setWeixin(customerDTO.getClueDTO().getClue().getWeixin());
//            excel.setQq(customerDTO.getClueDTO().getClue().getQq());
//            excel.setEmail(customerDTO.getClueDTO().getClue().getEmail());
//            excel.setAge(customerDTO.getClueDTO().getClue().getAge());
//            excel.setJob(customerDTO.getClueDTO().getClue().getJob());
//            excel.setYearIncome(customerDTO.getClueDTO().getClue().getYearIncome());
//            excel.setAddress(customerDTO.getClueDTO().getClue().getAddress());
//            excel.setNeedLoadName(ObjectUtils.isEmpty(customerDTO.getClueDTO().getNeedLoan().getTypeValue()) ? Constants.EMPTY : customerDTO.getClueDTO().getNeedLoan().getTypeValue());
//            excel.setProductName(ObjectUtils.isEmpty(customerDTO.getClueDTO().getIntentionProduct().getName()) ? Constants.EMPTY :customerDTO.getClueDTO().getIntentionProduct().getName());
//            excel.setSourceName(ObjectUtils.isEmpty(customerDTO.getClueDTO().getSource().getTypeValue()) ? Constants.EMPTY : customerDTO.getClueDTO().getSource().getTypeValue());
//            excel.setDescription(customerDTO.getClueDTO().getClue().getDescription());
//            excel.setNextContactTime(customerDTO.getNextContactTime());
//            list.add(excel);
//        });
        CustomerQuery query = new CustomerQuery();
        query.setIds(ids);
        return customerMapper.getAllOrByIdsCustomerExcel(query);
    }

    @Override
    public Customer getCustomerById(Integer id) {

        return customerMapper.selectByPrimaryKey(id);
    }
}
