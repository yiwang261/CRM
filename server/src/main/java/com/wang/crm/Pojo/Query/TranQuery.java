package com.wang.crm.Pojo.Query;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TranQuery extends BaseQuery {

    private Integer customerId;

    private BigDecimal money;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expectedDate;

    private Integer stage;

    private String description;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date nextContactTime;
}
