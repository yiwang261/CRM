package com.wang.crm.Controller;

import com.wang.crm.Result.ChartsData;
import com.wang.crm.Result.OverviewData;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticController {
    @Resource
    private StatisticService statisticService;
    @GetMapping("/api/statistic")
    public Result statistic() {
        OverviewData overviewData = statisticService.loadOverviewData();
        return Result.success(overviewData);
    }

    @GetMapping("/api/saleFunnel")
    public Result<List<ChartsData>> saleFunnel() {
        List<ChartsData> list =  statisticService.loadSaleFunnelData();
        return Result.success(list);
    }

    @GetMapping("/api/clueSource")
    public Result<List<ChartsData>> clueSource() {
        List<ChartsData> list =  statisticService.loadClueSourceData();
        return Result.success(list);
    }
}
