package com.wang.crm;

import com.wang.crm.Mapper.ActivityMapper;
import com.wang.crm.Mapper.ActivityRemarkMapper;
import com.wang.crm.Pojo.Entity.ActivityRemark;
import com.wang.crm.Pojo.Query.ActivityQuery;
import com.wang.crm.Pojo.Entity.Activity;
import com.wang.crm.Service.ActivityService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ActivityTest {
//    @Resource
//    private ActivityMapper activityMapper;
//    @Resource
//    private ActivityRemarkMapper activityRemarkMapper;
//
//    @Resource
//    ActivityService activityService;
//    @Test
//    public void test2() {
//        ActivityQuery dTO = new ActivityQuery();
//
////        dTO.setOwnerId(1);
//        activityMapper.getActivityDTOList(dTO).forEach(e->{
//            System.out.println(e);
//        });
//    }
//
//    @Test
//    public void testGetActivityDTOById(){
//        System.out.println(activityMapper.getActivityDTOById(1));
//    }
//
//    @Test
//    public void testAddActivity(){
//        Activity activity = new Activity();
//        activity.setOwnerId(1);
//        activity.setName("test1");
//        activity.setStartTime(new Date());
//        activity.setEndTime(new Date());
//        activity.setCost(new BigDecimal(100));
//        activity.setDescription("test1");
//        activity.setCreateBy(1);
//        activity.setCreateTime(new Date());
//        activityMapper.insert(activity);
//    }
//    @Test
//    public void testDeleteActivity(){
//        activityService.deleteActivity(62);
//    }
//    @Test
//    public void testDeleteBatch(){
//        activityService.deleteActivityBatch(new Integer[]{60,61});
//    }
//
//    @Test
//    public void testGet(){
//        List<Activity> onGoingActivityList = activityService.getOnGoingActivityList();
//        System.out.println(onGoingActivityList);
//    }
}
