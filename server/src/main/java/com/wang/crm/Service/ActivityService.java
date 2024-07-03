package com.wang.crm.Service;

import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.VO.ActivityVO;
import com.wang.crm.Pojo.DTO.ActivityDTO;
import com.wang.crm.Pojo.Query.ActivityQuery;
import com.wang.crm.Pojo.Entity.Activity;

import java.util.List;

public interface ActivityService {

    PageInfo<ActivityVO> getActivityList(int current, int size, ActivityQuery activityQuery);
    ActivityVO getActivityById(Integer id);
    int addActivity(Activity activity);
    int updateActivity(Activity activity);

    int deleteActivityBatch(Integer[] ids);
    int deleteActivity(Integer id);

    int updateActivityByActivityDTO(ActivityDTO activityDTO);
    int addActivityByActivityDTO(ActivityDTO activity);



    List<Activity> getOnGoingActivityList();
}
