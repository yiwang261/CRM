<script setup lang="ts">
import {onMounted, ref} from 'vue'
import {axiosInstance, get} from '@/api'
import {Color} from "@/assets/color";
import {getToken} from "@/utils/token";
import {ElMessageConfirm, tips, tipType} from "@/utils/tip";
import router from "@/router";

const customerQuery = ref({
  current: 1,
  size: 10,
  total: 0
})
const customerList = ref([{
  id: 1,
  phone:'',
  name:'',
  nextContactTime:'',
  clueDTO:{
    activity:{},
    appellation:{},
    intentionState:{},
    intentionProduct:{},
    owner: {},
    needLoan:{},
    source:{},
    state:{},
  }
}])
const getCustomerList = () => {
  get('/api/customer', customerQuery.value).then(res => {
    // console.log(res.data.data)
    customerList.value = res.data.data.list
    customerQuery.value.total = res.data.data.total
  })
}
const selectedActivityClue = ref<number[]>([]);
const handleSelectionChange = (list : any[]) => {
  let ids = list.map((item)=>{return item.id})
  if(ids){
    // console.log(ids)
    selectedActivityClue.value = ids;
  }
}
const exportChosenExcel = () => {
  if(selectedActivityClue.value.length===0){
    tips('请选择要导出的客户',tipType.warning);
    return;
  }
  let iframe = document.createElement('iframe');
  let token = getToken();
  iframe.src = axiosInstance.defaults.baseURL + '/api/customer/exportExcel?Authorization='+token+'&ids='+selectedActivityClue.value;
  document.body.appendChild(iframe);
  iframe.setAttribute("style", "display:none");

}
const exportAllExcel = () => {
  ElMessageConfirm('确定导出所有客户吗？', tipType.warning).then(()=>{
    let iframe = document.createElement('iframe');
    let token = getToken();
    iframe.src = axiosInstance.defaults.baseURL + '/api/customer/exportExcel?Authorization='+token;
    document.body.appendChild(iframe);
    iframe.setAttribute("style", "display:none");
  })
}

const ToDetail = (id:any) => {
  router.push('/dashboard/customer/'+id)
}

onMounted(()=>{
  getCustomerList()
})
</script>

<template>
<el-row style="margin: 20px;">
  <el-col>
    <el-button :color="Color.SECONDARY" @click="exportAllExcel" v-has-permission="'customer:export'">导出所有客户</el-button>
    <el-button :color="Color.POSITIVE" @click="exportChosenExcel"
               :disabled="selectedActivityClue.length===0" v-has-permission="'customer:export'">导出选中客户</el-button>
  </el-col>
</el-row>
  <el-divider/>
  <el-table :data="customerList"  @selection-change="handleSelectionChange" style="width: 100%;">
    <el-table-column type="index"/>
    <el-table-column type="selection" width="55" />
    <el-table-column label="负责人"      prop="clueDTO.owner.name"/>
    <el-table-column label="所属活动"     prop="clueDTO.activity.name"/>
    <el-table-column label="姓名"        prop="name">
      <template #default="scope">
        <el-tag  @click="ToDetail(scope.row.id)"style="cursor: pointer" type="primary"  >{{scope.row.name}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="称呼"        prop="clueDTO.appellation.typeValue"/>
    <el-table-column label="手机"        prop="phone"/>
    <el-table-column label="微信"        prop="weChat"/>
    <el-table-column label="是否贷款"     prop="clueDTO.needLoan.typeValue"/>
<!--    <el-table-column label="意向状态"     prop="clueDTO.intentionState.typeValue"/>-->
    <el-table-column label="意向状态"    >
      <template #default="scope">
        <el-tag :type="scope.row.clueDTO.intentionState.id == -1 ? 'success' : 'primary'">
          {{scope.row.clueDTO.intentionState.typeValue}}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column label="线索状态"     prop="clueDTO.state.typeValue"/>
    <el-table-column label="线索来源"     prop="clueDTO.source.typeValue"/>
    <el-table-column label="意向产品"     prop="clueDTO.intentionProduct.name"/>
    <el-table-column label="下次联系时间"  prop="nextContactTime"/>
    <el-table-column width="200px">
      <template #default="scope">
        <el-button :color="Color.POSITIVE"  plain v-has-permission="'customer:view'" @click="ToDetail(scope.row.id)">详情</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination
    background
    layout="prev, pager, next"
    :total="customerQuery.total"
    :current-page="customerQuery.current"
    @current-change="(current:number)=>{customerQuery.current = current;getCustomerList()}"
  />
</template>

<style scoped>

</style>