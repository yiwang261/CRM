package com.wang.crm.Mapper;

import com.wang.crm.Com.DataScope;
import com.wang.crm.Pojo.VO.ActivityVO;
import com.wang.crm.Pojo.Query.ActivityQuery;
import com.wang.crm.Pojo.Entity.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_activity(市场活动表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.Activity
*/
@Mapper
public interface ActivityMapper {

    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Activity record);

    Integer insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Activity record);

    Integer updateByPrimaryKey(Activity record);

    @DataScope(tableAlias = "t_u",tableField = "id")
    List<ActivityVO> getActivityDTOList(ActivityQuery activityQuery  );

    Integer deleteActivityBatch(Integer[] ids);

    ActivityVO getActivityDTOById(Integer id);

    List<Activity> getOnGoingActivityList();


    Integer getActivityCount();
}
