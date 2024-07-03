package com.wang.crm.Pojo.VO;

import com.wang.crm.Pojo.Entity.ActivityRemark;
import lombok.Data;

@Data
public class ActivityRemarkVO {
    private Integer activityRemarkId;
    private ActivityRemark activityRemark;
    private String editByName;
    private String createByName;

}
