package com.wang.crm.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.crm.Constant.Constants;
import com.wang.crm.Mapper.ActivityMapper;
import com.wang.crm.Mapper.ActivityRemarkMapper;
import com.wang.crm.Pojo.VO.ActivityVO;
import com.wang.crm.Pojo.DTO.ActivityDTO;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.ActivityQuery;
import com.wang.crm.Pojo.Entity.Activity;
import com.wang.crm.Service.ActivityService;
import com.wang.crm.Service.ClueService;
import com.wang.crm.Utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivityRemarkMapper activityRemarkMapper;
    @Resource
    private ClueService clueService;
    @Override
    public PageInfo<ActivityVO> getActivityList(int current, int size, ActivityQuery activityQuery) {
        if(size==0) size= Constants.DEFAULT_PAGE_SIZE;

        PageHelper.startPage(current,size);

        List<ActivityVO> activityList = activityMapper.getActivityDTOList(activityQuery);

        return new PageInfo<>(activityList);
    }

    @Override
    public ActivityVO getActivityById(Integer id) {
        if(id!=null)
            return activityMapper.getActivityDTOById(id);
        return null;
    }

    @Override
    public int addActivity(Activity activity) {
        if(activity!=null)
            return activityMapper.insert(activity);
        return 0;
    }

    @Override
    public int updateActivity(Activity activity) {
        if(activity!=null)
            return activityMapper.updateByPrimaryKey(activity);
        return 0;
    }

    @Transactional
    @Override
    public int deleteActivityBatch(Integer[] ids) {
        //如果ids为空，则返回0
        if(ids.length == 0) return 0;

        //删除线索
        clueService.deleteClueByActivityIdsBatch(ids);
        //删除活动备注
        activityRemarkMapper.deleteByActivityRemarkIdBatch(ids);


        //如果删除成功，则删除活动
        Integer i = activityMapper.deleteActivityBatch(ids);

        if(i == ids.length){
            return i;
        }else {
            throw new RuntimeException("删除失败");
        }

    }
    @Transactional
    @Override
    public int deleteActivity(Integer id) {
        if(id==null) return 0;
        //删除活动备注
        activityRemarkMapper.deleteByActivityRemarkId(id);

        //删除活动线索
        clueService.deleteClueByActivityId(id);

        //删除活动
        Integer i = activityMapper.deleteByPrimaryKey(id);
        if(i!=0){
            return i;
        }else {
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public int updateActivityByActivityDTO(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityDTO,activity);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activity.setEditBy(user.getId());
        return activityMapper.updateByPrimaryKeySelective(activity);
    }

    @Override
    public int addActivityByActivityDTO(ActivityDTO activityDTO) {
        Activity targetActivity = new Activity();
        BeanUtils.copyProperties(activityDTO,targetActivity);

//        System.out.println(targetActivity);
        //设置创建时间
        targetActivity.setCreateTime(new Date());

        //从token中获取创建人的id
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user!=null){
            targetActivity.setCreateBy(user.getId());
        }

        return activityMapper.insertSelective(targetActivity);
    }

    @Override
    public List<Activity> getOnGoingActivityList() {
        return activityMapper.getOnGoingActivityList();

    }


}
