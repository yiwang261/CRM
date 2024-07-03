package com.wang.crm.Service;

import com.wang.crm.Result.ChartsData;
import com.wang.crm.Result.OverviewData;

import java.util.List;

public interface StatisticService {

    OverviewData loadOverviewData();

    List<ChartsData> loadSaleFunnelData();

    List<ChartsData> loadClueSourceData();
}
