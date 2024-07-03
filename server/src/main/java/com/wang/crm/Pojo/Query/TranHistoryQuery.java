package com.wang.crm.Pojo.Query;



import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TranHistoryQuery extends BaseQuery {

    private Integer tranId;

    private Integer stage;

    private BigDecimal money;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expectedDate;
}
