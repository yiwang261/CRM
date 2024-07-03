package com.wang.crm.Controller;

import com.wang.crm.Pojo.Entity.ActivityRemark;
import com.wang.crm.Pojo.Query.ActivityRemarkQuery;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.ActivityRemarkService;
import com.wang.crm.Service.Impl.ActivityRemarkServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity/remark")
public class ActivityRemarkController {

    @Resource
    private ActivityRemarkService activityRemarkService;
    @PostMapping("/{id}")
    public Result addActivityRemark(@RequestBody ActivityRemarkQuery remark, @PathVariable("id") Integer activityId){

        int i = activityRemarkService.saveActivityRemark(activityId,remark);
//        int i = activityService.save(id,);

        return i==0?Result.error("添加失败"):Result.success();
    }

    @GetMapping("/{id}")
    public Result getActivityRemarkList(@PathVariable("id") Integer id,
            @RequestParam(value = "current",required = false,defaultValue = "1")Integer current,
            @RequestParam(value = "size",required = false,defaultValue = "4")  Integer size){

        return Result.success(activityRemarkService.getActivityRemarkList(id,current,size));
    }


    @PutMapping
    public Result updateActivityRemark(@RequestBody ActivityRemarkQuery remark){
        int i = activityRemarkService.updateActivityRemark(remark);

        return i==0?Result.error("修改失败"):Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteActivityRemark(@PathVariable("id") Integer id){
        int deleted = activityRemarkService.deleteActivityRemarkById(id);
        return deleted==1?Result.success():Result.error("删除失败");
    }
}
