package com.wang.crm.Controller;


import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.Entity.CustomerRemark;
import com.wang.crm.Pojo.Query.CustomerRemarkQuery;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.CustomerRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerRemarkController {

    @Resource
    private CustomerRemarkService customerRemarkService;

    /**
     * 分页查询线索跟踪记录
     *
     * @param customerId
     * @param current
     * @return
     */
    @GetMapping(value = "/api/customer/{customerId}/remark")
    public Result<PageInfo<CustomerRemark>> customerRemarkPage(@PathVariable(value = "customerId") Integer customerId,
                            @RequestParam(value = "current") Integer current) {
        if (current == null) {
            current = 1;
        }

        PageInfo<CustomerRemark> pageInfo = customerRemarkService.getCustomerRemarkByPage(current, customerId);
        return Result.success(pageInfo);
    }

    /**
     * 提交客户跟踪记录（保存）
     *
     * @param customerRemarkQuery
     * @param
     * @return
     */
    @PostMapping(value = "/api/customer/remark")
    public Result addCustomerRemark(@RequestBody CustomerRemarkQuery customerRemarkQuery) {


        int save = customerRemarkService.saveCustomerRemark(customerRemarkQuery);

        return save >= 1 ? Result.success() : Result.error();
    }
}
