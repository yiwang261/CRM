package com.wang.crm.Controller;


import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.DTO.TranDTO;
import com.wang.crm.Pojo.Entity.Tran;
import com.wang.crm.Pojo.Query.TranQuery;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.TranService;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.*;

@RestController
public class TranController {

    @Resource
    private TranService tranService;

    /**
     * 创建交易
     *
     * @param tranQuery
     * @return
     */
    @PostMapping(value = "/api/customer/tran")
    public Result createTran(@RequestBody TranQuery tranQuery) {

        int save = tranService.saveTran(tranQuery);
        return save >= 1 ? Result.success() : Result.error();
    }


    /**
     * 分页查询交易列表数据
     *
     * @param current
     * @return
     */
    @GetMapping(value = "/api/tran")
    public Result<PageInfo<TranDTO>> tranPage(@RequestParam(value = "current", required = false,defaultValue = "1") Integer current) {
        System.out.println(current);
        PageInfo<TranDTO> pageInfo = tranService.getTranByPage(current);
        return Result.success(pageInfo);
    }

    /**
     * 查询交易详情
     *
     * @param tranId
     * @return
     */
    @GetMapping(value = "/api/tran/{tranId}")
    public Result<TranDTO> tranDetail(@PathVariable(value = "tranId") Integer tranId) {
        TranDTO tTran = tranService.getTranById(tranId);
        return Result.success(tTran);
    }
}
