package com.wang.crm.Manager;

import com.wang.crm.Mapper.ActivityMapper;
import com.wang.crm.Mapper.ClueMapper;
import com.wang.crm.Mapper.CustomerMapper;
import com.wang.crm.Mapper.TranMapper;
import com.wang.crm.Result.ChartsData;
import com.wang.crm.Result.OverviewData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class StatisticManager {
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private ClueMapper clueMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private TranMapper tranMapper;
    private OverviewData overviewData;
    public OverviewData loadOverviewData() {
         Integer effectiveActivityCount = activityMapper.getOnGoingActivityList().size();
        //总活动
         Integer totalActivityCount = activityMapper.getActivityCount();
        //总线索
         Integer totalClueCount = clueMapper.getClueCount() ;
        //总客户
         Integer totalCustomerCount = customerMapper.getCustomerCount();
        //成功交易金额
         BigDecimal successTranMoney = tranMapper.getSuccessTranMoney();
        //总交易金额
         BigDecimal totalTranAmount = tranMapper.getTotalTranAmount();
        return OverviewData.builder()
                .effectiveActivityCount(effectiveActivityCount)
                .totalActivityCount(totalActivityCount)
                .totalClueCount(totalClueCount)
                .totalTranAmount(totalTranAmount)
                .totalCustomerCount(totalCustomerCount)
                .successTranMoney(successTranMoney).build();
    }

    public List<ChartsData> loadSaleFunnelData() {
        Integer clueCount = clueMapper.getClueCount() ;
        Integer customerCount = customerMapper.getCustomerCount();
        Integer tranCount = tranMapper.getTranCount();
        Integer successTranCount = tranMapper.getSuccessTranCount();
        return List.of(
                ChartsData.builder().name("成交").value(successTranCount).build(),
                ChartsData.builder().name("客户").value(customerCount).build(),
                ChartsData.builder().name("交易").value(tranCount).build(),
                ChartsData.builder().name("线索").value(clueCount).build()
        );
    }

    public List<ChartsData> loadClueSourceData() {

        return clueMapper.getClueSourceList();
    }
}
