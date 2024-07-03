package com.wang.crm.Result;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverviewData {
    //有效的活动
    private Integer effectiveActivityCount;
    //总活动
    private Integer totalActivityCount;
    //总线索
    private Integer totalClueCount;
    //总客户
    private Integer totalCustomerCount;
    //成功交易金额
    private BigDecimal successTranMoney;
    //总交易金额
    private BigDecimal totalTranAmount;

}
