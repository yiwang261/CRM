<script setup lang="ts">
import {onMounted, ref} from 'vue'
import { useTransition } from '@vueuse/core'
import {get} from '@/api'
import * as echarts from 'echarts';





/*
* 获取概览数据
*/
const overviewData = ref({
  effectiveActivityCount:0,
  totalActivityCount:0,
  totalClueCount:0,
  totalTranAmount:0,
  totalCustomerCount:0,
  successTranMoney:0,
})

const loadOverview = ()=>{
  get('/api/statistic').then(res=>{
    if (res.data.code === 200){
      overviewData.value = res.data.data;
      // totalTranAmount.value = res.data.data.totalTranAmount
    }
  }).catch(err=>{
    console.log(err)
  })
}

const loadClueSourceChartData = () => {
  get('/api/clueSource').then(res=>{
    if(res.data.code === 200){
      loadClueSourceChart(res.data.data)
    }
  })
  
}
const loadClueSourceChart = (data:any) => {
  let chartDom = document.getElementById('clueSourceChart');
  let ClueFromChart = echarts.init(chartDom);
  let option;

  option = {
    title: {
      text: '线索来源统计',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data:data,
        // data: [
        //   { value: 1048, name: 'Search Engine' },
        //   { value: 735, name: 'Direct' },
        //   { value: 580, name: 'Email' },
        //   { value: 484, name: 'Union Ads' },
        //   { value: 300, name: 'Video Ads' }
        // ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  };
  option && ClueFromChart.setOption(option);
  
}

const loadSaleFunnelChartData = ()=>{
  get('/api/saleFunnel').then(res=>{
    if(res.data.code === 200){
      loadSaleFunnelChart(res.data.data)
    }
  })
}
const loadSaleFunnelChart = (data:any)=>{
  let chartDom: HTMLElement = document.getElementById('saleFunnelChart') as HTMLElement;
  let SaleFunnelChart = echarts.init(chartDom);
  let option;
  option = {
    title: {
      text: '销售漏斗图',
      left:'center',
      top: 'bottom'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c}%'
    },
    toolbox: {
      feature: {
        dataView: { readOnly: false },
        restore: {},
        saveAsImage: {}
      }
    },
    legend: {
      data: [ '成交','线索', '客户', '交易']
    },
    series: [
      {
        name: '销售漏斗图统计数据',
        type: 'funnel',
        left: '10%',
        top: 60,
        bottom: 60,
        width: '80%',
        min: 0,
        max: 100,
        minSize: '0%',
        maxSize: '100%',
        sort: 'descending',
        gap: 2,
        label: {
          show: true,
          position: 'inside'
        },
        labelLine: {
          length: 10,
          lineStyle: {
            width: 1,
            type: 'solid'
          }
        },
        itemStyle: {
          borderColor: '#fff',
          borderWidth: 1
        },
        emphasis: {
          label: {
            fontSize: 20
          }
        },
        // data: [
        //   { value: 60, name: '成交' },
        //   { value: 40, name: '交易' },
        //   { value: 20, name: '客户' },
        //   { value: 80, name: '线索' },
        // ]
        data: data
      }
    ]
  };
  option && SaleFunnelChart.setOption(option);
}

onMounted(()=>{
  loadOverview();
  loadSaleFunnelChartData();
  loadClueSourceChartData();
})
</script>

<template>
  <div style="padding: 5px;height: 100%;margin-top: 10px;">
    <el-card style="padding-left: 100px;" >
      <el-row justify="center">
        <el-col :span="6">
          <el-statistic :value="overviewData.effectiveActivityCount">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                市场活动
              </div>
            </template>
            <template #suffix>/{{ overviewData.totalActivityCount }}</template>
          </el-statistic>
        </el-col>
        <el-col :span="6">
          <el-statistic title="线索总数" :value="overviewData.totalClueCount" />
        </el-col>


        <el-col :span="6">
          <el-statistic title="客户总数" :value="overviewData.totalCustomerCount" />
        </el-col>

        <el-col :span="6">
          <el-statistic :value="overviewData.successTranMoney">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                交易总额
              </div>
            </template>
            <template #suffix>/{{ overviewData.totalTranAmount }}</template>
          </el-statistic>
        </el-col>
      </el-row>
    </el-card>


    <el-card style="width: 48%;height: 350px;margin-top: 50px;float: left;">
      <div id="saleFunnelChart" style="width: 100%;height: 320px;"/>
    </el-card>



    <el-card style="width: 48%;height: 350px;margin-top: 50px;float: left;margin-left: 40px;">
      <div id="clueSourceChart" style="width: 100%;height: 320px;"/>
    </el-card>




  </div>



</template>

<style scoped>

</style>