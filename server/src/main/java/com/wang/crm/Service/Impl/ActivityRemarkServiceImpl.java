package com.wang.crm.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.crm.Mapper.ActivityRemarkMapper;
import com.wang.crm.Pojo.Query.ActivityRemarkQuery;
import com.wang.crm.Pojo.Entity.ActivityRemark;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.VO.ActivityRemarkVO;
import com.wang.crm.Service.ActivityRemarkService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Resource
    private ActivityRemarkMapper remarkMapper;
    public int saveActivityRemark(Integer activityId, ActivityRemarkQuery remark) {
        if(ObjectUtils.isEmpty(remark))return 0;
        ActivityRemark activityRemark = new ActivityRemark();

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        activityRemark.setActivityId(activityId);
        activityRemark.setNoteContent(remark.getRemark());
        activityRemark.setCreateBy(user.getId());
        activityRemark.setCreateTime(new Date());


        return remarkMapper.insert(activityRemark);
    }

    public PageInfo<ActivityRemarkVO> getActivityRemarkList(Integer id, Integer current, Integer size) {
        PageHelper.startPage(current, size);

        ActivityRemarkQuery activityRemarkQuery = new ActivityRemarkQuery();
        activityRemarkQuery.setActivityId(id);

        List<ActivityRemarkVO> list = remarkMapper.getActivityRemarkVOById(activityRemarkQuery);
        PageInfo<ActivityRemarkVO> page = new PageInfo<>(list);

        return page;
    }

    @Override
    public int updateActivityRemark(ActivityRemarkQuery remarkQuery) {
        ActivityRemark activityRemark = new ActivityRemark();

        BeanUtils.copyProperties(remarkQuery,activityRemark);

        activityRemark.setEditTime(new Date());
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activityRemark.setEditBy(user.getId());

        return remarkMapper.updateByPrimaryKeySelective(activityRemark);
    }

    @Override
    public int deleteActivityRemarkById(Integer id) {
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setDeleted(1);
        return remarkMapper.updateByPrimaryKeySelective(activityRemark);
    }


}
