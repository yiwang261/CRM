<template>
  <MarketActivityDialog :visible="dialogVisible" :mode="activityDialogMode"
    :acactivityId="activityId"
   @refresh-data="handleRefreshData()" 
   @visible-change="handleVisiableChange()"/>
<!--  <MarketActivityDialog />-->

  <div style="padding: 20px;">
    <el-form  :rules="searchRules" :inline="true" :model="activityQuery" ref="searchFormRef">
      <el-row >
        <el-col :span="6">
          <el-form-item label="负责人" label-width="68px" prop="ownerId">
            <el-select v-model="activityQuery.ownerId"
                       clearable placeholder="请选择负责人"
                       style="width: 200px;"
                       @click="getOwner()"
            >
              <el-option
                v-for="owner in ownerOptions"
                :key="owner.id"
                :value="owner.id"
                :label="owner.name"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="活动名称" prop="name">
            <el-input v-model.trim="activityQuery.name" style="width: 220px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="创建时间" prop="createTime">
            <el-date-picker
                v-model="activityQuery.createTime"
                type="datetime"
                placeholder="前选择创建时间"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
                date-format="YYYY/MM/DD ddd"
                time-format="A hh:mm:ss"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <el-form-item label="活动预算" prop="cost">
            <el-input placeholder="预算" v-model.trim="activityQuery.cost"/>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker
                v-model="activityQuery.startTime"
                type="datetime"
                placeholder="请选择开始时间"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
                date-format="YYYY/MM/DD ddd"
                time-format="A hh:mm:ss"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker
                v-model="activityQuery.endTime"
                type="datetime"
                placeholder="请选择结束时间"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
                date-format="YYYY/MM/DD ddd"
                time-format="A hh:mm:ss"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6" >
          <el-form-item>
            <el-button :color="Color.PRIMARY" @click="onSearch()" v-has-permission="'activity:search'">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button :color="Color.SECONDARY" plain @click="onReset">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>

    <el-button :color="Color.PRIMARY" :plain="true" size="small" @click="onAddActivity()" v-has-permission="'activity:add'">新增活动</el-button>

    <el-button :color="Color.NEGATIVE"  :plain="true" size="small" :disabled="!computedDelRef"
               v-has-permission="'activity:delete'"
               @click="onDeleteActivityBatch()">批量删除活动</el-button>

    <el-divider />

    <el-table
        ref="multipleTableRef"
        :data="activityList"
        style="width: 100%"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column type="index"                    label="序号"    width="55"/>
      <el-table-column property="ownerName"            label="负责人"   width="80" />
      <el-table-column property="activity.name"        label="活动名称" show-overflow-tooltip />
      <el-table-column property="activity.startTime"   label="开始时间" show-overflow-tooltip />
      <el-table-column property="activity.endTime"     label="结束时间" show-overflow-tooltip />
      <el-table-column property="activity.cost"        label="活动预算" show-overflow-tooltip />
      <el-table-column property="activity.createTime"  label="创建时间" show-overflow-tooltip />
      <el-table-column           min-width="150"       label="操作"     show-overflow-tooltip  >
        <template #default="scope">
          <el-button :color="Color.PRIMARY"   @click="showDetail(scope.row.activityId)" v-has-permission="'activity:view'">详情</el-button>
          <el-button :color="Color.WARING"  style="color: white"  @click="updateActivity(scope.row.activityId)" v-has-permission="'activity:edit'">修改</el-button>
          <el-button :color="Color.NEGATIVE"    @click="deleteActivity(scope.row.activityId)" v-has-permission="'activity:delete'">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top: 20px;margin-left: 20px;"
                   background
                   layout="prev, pager, next"
                   :total="total"
                   :page-size="8"
                   v-model:current-page="current"
    />
  </div>

</template>

<script lang="ts" setup>
import {computed, onMounted, reactive, ref, watch} from 'vue'
import {ElTable, type FormInstance, type FormRules} from "element-plus";
import {del, get} from "@/api";
import {ElMessageConfirm, tips, tipType} from "@/utils/tip";
import type {activityDTO, optionType,activityQuery} from "@/interface/activity";
import {modeType} from "@/interface/enum";
import router from '@/router';
import { Color } from "@/assets/color";


const handleVisiableChange= () => {
  dialogVisible.value = false
}
const handleRefreshData = () =>{
  // current.value, pageSize.value
  getActivityList(current.value,pageSize.value);
}

// 修改或添加  复用一个弹窗
const dialogVisible = ref<boolean>(false);
const activityDialogMode = ref<modeType>(modeType.add);
const onAddActivity = ()=>{
  dialogVisible.value = !dialogVisible.value;
  activityDialogMode.value = modeType.add;
}
//修改
const activityId = ref<number>();
const updateActivity = (id:number) =>{
  dialogVisible.value = true;
  activityDialogMode.value = modeType.update;
  activityId.value = id;
}

//详情
const showDetail = (id:number) =>{
  router.push("/dashboard/MarketActivity/"+id)
}



//删除
const deleteActivity = (id:number) =>{
  ElMessageConfirm("确认删除",tipType.warning).then(()=>{
    del("/api/activity/"+id).then((res)=>{
      if(res.data.code == 200){
        tips(res.data.msg,tipType.success);
        getActivityList(current.value,pageSize.value);
      }else{
        tips(res.data.msg,tipType.error);
      }
    }).catch(error=>{

    })
  })

  
}

//批量删除
const onDeleteActivityBatch = ()=>{
    console.log(selected.value[0]);
    
    del("/api/activity",selected.value
    ).then((res)=>{
      if(res.data.code == 200){
        tips(res.data.msg,tipType.success);
        getActivityList(current.value,pageSize.value);
      }else{
        tips(res.data.msg,tipType.error);
      }
    }).catch(error=>{

    })
}


// 查询活动条件表单
const searchFormRef = ref<FormInstance>();
const activityQuery = reactive<activityQuery>({
  ownerId: null,
  name: '',
  startTime: '',
  endTime: '',
  cost: '',
  createTime:'',
})
const searchRules = reactive<FormRules<activityQuery>>({
  cost: [
      {pattern: /(0|[1-9]\d*)(\.\d{1,2})?$/, message: '必须为数字值,最多精确到2位',trigger:'change'}
  ],
})
const onSearch = () => {
  getActivityList(current.value, pageSize.value)
}
const onReset = () => {
  searchFormRef.value?.resetFields();
}

//获取所有负责人
const ownerOptions = ref<[optionType]>();
const getOwner = () => {
  get("/api/owner",{}).then(res=>{
    ownerOptions.value = res.data.data;
  }).catch(err=>{
    tips(err.message,tipType.error)
  })
}



// 分页查询活动列表
const current = ref<number>(1);
const total = ref<number>(0);
const pageSize = ref<number>(8);



const activityList = ref<activityDTO[]>([{
  activity:{
    id:0,
    ownerId:0,
    name:'',
    startTime:'',
    endTime:'',
    cost:'',
    description:'',
    createTime:'',
    createBy:'',
    editTime:'',
    editBy:'',
  },
  ownerName:'',
  activityId:0,
}]);

const getActivityList = (current: number, size: number) => {
  //1 params 但是有json
  // get("/api/activity", {
  //   current: current,
  //   size: size,
  //   activityQuery: JSON.stringify(activityQuery)
  // }).then(res=>{
  //   total.value = res.data.data.total;
  //   activityList.value = res.data.data.list;
  // }).catch(err=>{
  //   console.log(err)
  // })
    //2  解构 params的方式
    get("/api/activity", {
      current: current,
      size: size,
      ...activityQuery
    }).then(res=>{
      // console.log(res.data.data)
      total.value = res.data.data.total;
      activityList.value = res.data.data.list;
    }).catch(err=>{
      console.log(err)
    })


}

const selected = ref<number[]>([]);
const handleSelectionChange = (activities:activityDTO[])=>{
    selected.value = activities.map((activity:activityDTO)=>activity.activityId);
}



onMounted(()=>{
  getActivityList(current.value, pageSize.value);
})

watch(current,()=>{
  getActivityList(current.value, pageSize.value);
})

const computedDelRef = computed(() => {
  if(selected.value?.length<=0 || total.value<=0){
    return false;
  }else {
    return true;
  }
})
</script>

<style scoped>

</style>
