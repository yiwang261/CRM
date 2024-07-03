package com.wang.crm.Controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.DTO.CustomerDTO;
import com.wang.crm.Pojo.Entity.Customer;
import com.wang.crm.Pojo.Query.CustomerQuery;
import com.wang.crm.Result.CustomerExcel;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.CustomerService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yiwan
 */
@RestController
@RequestMapping("/api")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    /***
     * 线索转换为客户
     * @param customer
     * @return
     */
    @PreAuthorize("hasAnyAuthority('customer:add')")
    @PostMapping("/customer")
    public Result addCustomer(@RequestBody Customer customer) {
        int i = customerService.addCustomer(customer);
        return i==1?Result.success():Result.error();
    }


    @PreAuthorize("hasAnyAuthority('customer:list')")
    @GetMapping("/customer")
    public Result<PageInfo<CustomerDTO>> getAllCustomer(CustomerQuery query) {
        PageInfo<CustomerDTO> customers =customerService.getAllCustomerDTOByPage(query);
        return Result.success(customers);
    }

    @GetMapping("/customer/{id}")
    public Result<Customer> clueDetail(@PathVariable(value = "id") Integer id) {
        Customer customer = customerService.getCustomerById(id);

        return Result.success(customer);
    }

    @PreAuthorize("hasAnyAuthority('customer:export')")
    @GetMapping("/customer/exportExcel")
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(name = "ids", required = false,defaultValue = "") List<Integer> ids) throws IOException {
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + URLEncoder.encode("客户信息"+ System.currentTimeMillis() + ".xlsx", StandardCharsets.UTF_8));


        List<CustomerExcel> list = customerService.exportAllCustomerExcel(ids);


        EasyExcel.write(response.getOutputStream(), CustomerExcel.class)
                .sheet().doWrite(list);
    }
}
