package com.wang.crm.Controller;


import com.wang.crm.Pojo.Entity.TranHistory;
import com.wang.crm.Pojo.Query.TranHistoryQuery;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.TranHistoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author yiwan
 */
@RestController
public class TranHistoryController {

    @Resource
    private TranHistoryService tranHistoryService;

    @PostMapping(value = "/api/tran/history")
    public Result addTranHistory(@RequestBody TranHistoryQuery tranHistoryQuery) {
        //1、前端axios的post提交过来的参数，是一个json，后端接收要使用@RequestBody注解接收，代码如下：
        /**
         *       doPost("/api/activity/remark", {
         *         activityId : this.activityDetail.id,
         *         noteContent : this.activityRemark.noteContent
         *       })
         */
        //2、前端axios的post提交一个formData，此时可以使用@RequestParam或者XxxQuery对象接收，代码如下：
        /**
         *       doPost("/api/user", formData)
         */

        int save = tranHistoryService.saveTranHistory(tranHistoryQuery);

        return save >= 1 ?Result.success() : Result.error();
    }

    /**
     * 查询交易历史/阶段记录
     *
     * @param tranId
     * @return
     */
    @GetMapping(value = "/api/tran/{tranId}/history")
    public Result<List<TranHistory>> tranRemarkPage(@PathVariable(value = "tranId") Integer tranId) {
        List<TranHistory> tTranHistoryList = tranHistoryService.getTranHistoryByTranId(tranId);
        return Result.success(tTranHistoryList);
    }
}
