package com.wang.crm.Mapper;

import com.wang.crm.Com.DataScope;
import com.wang.crm.Pojo.Entity.ActivityRemark;
import com.wang.crm.Pojo.Query.ActivityRemarkQuery;
import com.wang.crm.Pojo.VO.ActivityRemarkVO;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_activity_remark(市场活动备注表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.ActivityRemark
*/
@Mapper
public interface ActivityRemarkMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ActivityRemark record);

    int insertSelective(ActivityRemark record);

    ActivityRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityRemark record);

    int updateByPrimaryKey(ActivityRemark record);

    //根据活动id查询备注
    @DataScope(tableAlias = "t_remark",tableField = "create_by")
    List<ActivityRemarkVO> getActivityRemarkVOById(ActivityRemarkQuery activityRemarkQuery);

    //根据id批量删除活动备注
    int deleteByActivityRemarkIdBatch(Integer[] ids);
    //根据备注id删除活动备注
    int deleteByActivityRemarkId(Integer id);
}
