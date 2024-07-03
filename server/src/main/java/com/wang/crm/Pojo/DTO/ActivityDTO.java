package com.wang.crm.Pojo.DTO;

import com.alibaba.fastjson2.annotation.JSONField;
import com.wang.crm.Pojo.Entity.Activity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ActivityDTO {
    /**
     * 主键，自动增长，活动ID
     */
    private Integer id;

    /**
     * 活动所属人ID
     */
    private Integer ownerId;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 活动预算
     */
    private BigDecimal cost;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 活动创建人
     */
    private Integer createBy;
}
