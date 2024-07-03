<template>
  <el-table
      :data="tranList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="50"/>
    <el-table-column type="index" label="序号" width="65"/>
    <el-table-column label="交易流水号">
      <template #default="scope">
        <el-tag type="warning" style="cursor: pointer" href="javascript:" @click="view(scope.row.id)">{{ scope.row.tranNo }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="clueDO.fullName" label="客户姓名"/>
    <el-table-column property="clueDO.phone" label="客户手机"/>
    <el-table-column property="money" label="交易金额"/>
    <el-table-column property="expectedDate" label="预计成交时间"/>
    <el-table-column property="stageValue" label="交易阶段">
      <template #default="scope">
        <el-tag :type="scope.row.stageValue === '01创建交易' ? 'info' : 'success'">{{ scope.row.stageValue }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="stageValue" label="交易阶段"/>
    <el-table-column property="nextContactTime" label="下次跟踪时间"/>
    <el-table-column property="createTime" label="创建时间"/>
    <el-table-column label="操作" width="90">
      <template #default="scope">
        <el-button plain :color="Color.SECONDARY" @click="view(scope.row.id)">详情</el-button>
      </template>
    </el-table-column>
  </el-table>


  <el-pagination style="margin-top: 20px;margin-left: 20px;"
                 background
                 layout="prev, pager, next"
                 :total="total"
                 :page-size="pageSize"
                 @change="getData"
                 v-model:current-page="currentPage"
  />
<!--  <p>-->
<!--    <el-pagination-->
<!--        background-->
<!--        layout="prev, pager, next"-->
<!--        :page-size="pageSize"-->
<!--        :total="total"-->
<!--        @prev-click="page"-->
<!--        @next-click="page"-->
<!--        @current-change="page"/>-->
<!--  </p>-->
</template>

<script setup lang="ts">
import {get} from '@/api'
import router from "@/router";
import {onMounted, ref} from 'vue'
import {Color} from "@/assets/color";

const tranList =ref( [{
  clueDO : {},
  createByDO:{},
  editByDO:{}

}])

const currentPage = ref(1)
const pageSize = ref(0)

const total = ref(0)
    //分页时每页显示多少条数据
    //总共有多少条

const getData = async () =>{
 await get("/api/tran", {
    current : currentPage.value //当前页，前面是参数名，后面是参数值
  }).then(resp => {
    if (resp.data.code === 200) {
      // console.log(resp.data.data)
      tranList.value = resp.data.data.list; // resp.data  =  R 对象

      pageSize.value = resp.data.data.pageSize;
      total.value = resp.data.data.total;
    }
  })
}

const view = (id:any) =>{
  router.push("/dashboard/tran/detail/" + id);
}

const handleSelectionChange = (val:any) =>{
  // console.log(val)
}
onMounted(()=>{
  getData();

})

</script>

<style scoped>

</style>