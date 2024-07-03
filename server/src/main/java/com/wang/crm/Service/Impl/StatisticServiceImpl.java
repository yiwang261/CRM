package com.wang.crm.Service.Impl;

import com.wang.crm.Manager.StatisticManager;
import com.wang.crm.Result.ChartsData;
import com.wang.crm.Result.OverviewData;
import com.wang.crm.Service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    //统计数据
    @Resource
    private StatisticManager statisticManager;
    @Override
    public OverviewData loadOverviewData() {
        return statisticManager.loadOverviewData();
    }

    @Override
    public List<ChartsData> loadSaleFunnelData() {
        return statisticManager.loadSaleFunnelData();
    }

    @Override
    public List<ChartsData> loadClueSourceData() {


        return statisticManager.loadClueSourceData();
    }
}
