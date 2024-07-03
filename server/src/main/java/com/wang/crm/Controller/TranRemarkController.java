package com.wang.crm.Controller;


import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.Entity.TranRemark;
import com.wang.crm.Pojo.Query.TranRemarkQuery;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.TranRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author yiwan
 */
@RestController
public class TranRemarkController {

    @Resource
    private TranRemarkService tranRemarkService;

    @PostMapping(value = "/api/tran/remark")
    public Result addTranRemark(@RequestBody TranRemarkQuery tranRemarkQuery) {
//      System.out.println("tranRemarkQuery = " + tranRemarkQuery);

        int save = tranRemarkService.saveTranRemark(tranRemarkQuery);

        return save >= 1 ? Result.success() : Result.error();
    }

    /**
     * 分页查询交易跟踪记录
     *
     * @param tranId
     * @param current
     * @return
     */
    @GetMapping(value = "/api/tran/{tranId}/remark")
    public Result<PageInfo<TranRemark>> tranRemarkPage(@PathVariable(value = "tranId") Integer tranId,
                            @RequestParam(value = "current") Integer current) {
        if (current == null) {
            current = 1;
        }

        PageInfo<TranRemark> pageInfo = tranRemarkService.getTranRemarkByPage(current, tranId);
//        System.out.println("pageInfo = " + pageInfo);
        return Result.success(pageInfo);
    }

}
