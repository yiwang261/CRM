package com.wang.crm.Service;

import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.Query.ActivityRemarkQuery;
import com.wang.crm.Pojo.Entity.ActivityRemark;
import com.wang.crm.Pojo.VO.ActivityRemarkVO;

import java.util.List;

public interface ActivityRemarkService {
    int saveActivityRemark(Integer activityId, ActivityRemarkQuery remark);

    PageInfo<ActivityRemarkVO> getActivityRemarkList(Integer id, Integer current, Integer size);

    int updateActivityRemark(ActivityRemarkQuery remarkQuery);

    int deleteActivityRemarkById(Integer id);

}
