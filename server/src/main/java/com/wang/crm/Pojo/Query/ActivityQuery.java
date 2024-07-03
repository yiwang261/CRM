package com.wang.crm.Pojo.Query;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ActivityQuery extends BaseQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 活动所属人ID
     */
    private Integer ownerId;

    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动预算
     */

    private BigDecimal cost;
    /**
     * 活动开始时间
     */

    private String startTime;

    /**
     * 活动结束时间
     */
    private String endTime;


    /**
     * 活动创建时间
     */

    private String createTime;
}
