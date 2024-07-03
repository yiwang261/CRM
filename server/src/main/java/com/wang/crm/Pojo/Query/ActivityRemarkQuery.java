package com.wang.crm.Pojo.Query;

import lombok.Data;

@Data
public class ActivityRemarkQuery extends BaseQuery {
    private Integer activityId;
    private Integer id;
    private String  remark;
    private String  noteContent;
}
