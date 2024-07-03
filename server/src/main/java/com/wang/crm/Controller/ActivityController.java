package com.wang.crm.Controller;

import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.VO.ActivityVO;
import com.wang.crm.Pojo.DTO.ActivityDTO;
import com.wang.crm.Pojo.Query.ActivityQuery;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.ActivityService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    //1 activityQuery在请求路径中是字符串 进行转换 json转换
//    @GetMapping
//    public Result<PageInfo<ActivityDTO>> getActivityListByPage(@RequestParam("current")Integer current,
//                                                               @RequestParam("size") Integer size,
//                                                               @RequestParam("activityQuery")String activityQuery){
//
//        ActivityQuery bean = JSONUtils.toBean(activityQuery, ActivityQuery.class);
//        System.out.println(bean);
//        PageInfo<ActivityDTO> pageInfo = activityService.getActivityList(current, size, bean);
//
//        return Result.success(pageInfo);
//    }

    //2直接将所有的参数拆开用?a=1&b=2&c=3&d=4&e=5&f=6&g=7&h=83 的方式  前端用...解构
    @PreAuthorize("hasAnyAuthority('activity:list')")
    @GetMapping
    public Result<PageInfo<ActivityVO>> getActivityListByPage(ActivityQuery activityQuery){

        PageInfo<ActivityVO> pageInfo = activityService.getActivityList(activityQuery.getCurrent()
                , activityQuery.getSize(), activityQuery);

        return Result.success(pageInfo);
    }

    @PreAuthorize("hasAnyAuthority('activity:view')")
    @GetMapping("/{id}")
    public Result<ActivityVO> getActivityById(@PathVariable("id") Integer id){
        return Result.success(activityService.getActivityById(id));
    }
    @PreAuthorize("hasAnyAuthority('activity:add')")
    @PostMapping
    public Result addActivity(@RequestBody ActivityDTO activity){

        int i = activityService.addActivityByActivityDTO(activity);
        return i==0?Result.error("添加失败"):Result.success();
    }
    @PreAuthorize("hasAnyAuthority('activity:edit')")

    @PutMapping("/{id}")
    public Result updateActivity(@RequestBody ActivityDTO activity,@PathVariable("id") Integer id ){

        int i = activityService.updateActivityByActivityDTO(activity);
        return i==0?Result.error("修改失败"):Result.success();
    }
    @PreAuthorize("hasAnyAuthority('activity:delete')")
    @DeleteMapping("/{id}")
    public Result deleteActivity(@PathVariable("id") Integer id){
        System.out.println(id);
        int i = activityService.deleteActivity(id);
        return i==0?Result.error("删除失败"):Result.success();
    }
    @PreAuthorize("hasAnyAuthority('activity:delete')")

    @DeleteMapping
    public Result deleteActivityBatch(@RequestBody Integer[] ids){

        int i = activityService.deleteActivityBatch(ids);
        return i==0?Result.error("删除失败"):Result.success();
    }

}
