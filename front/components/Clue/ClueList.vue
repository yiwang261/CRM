<script setup lang="ts">
import type {ActivityClueQuery, ActivityClueVO} from '@/interface/ActivityClue';
import router from '@/router';
import {computed, onMounted, ref, watch} from 'vue'
import {Color} from "@/assets/color";
import {del, get, post} from "@/api"
import type {UploadInstance} from "element-plus";
import {tips, tipType} from "@/utils/tip";

const total = ref<number>(0);
const activityClueQuery = ref<ActivityClueQuery>({
  currentPage:1,
  size:10,
});

const clueList = ref([{
  clue:{

  },
  owner:{
  },
  activity:{

  },
  appellation:{

  },
  state:{

  },
  source:{

  },
  needLoan:{

  }

}]);

const getActivityClue = async () => {
 await get("/api/clue",{
    current:activityClueQuery.value.currentPage,
    size:activityClueQuery.value.size,
  }).then(res=>{
     clueList.value = res.data.data.list;
     total.value = res.data.data.total;
  }).catch(err=>{
    console.log(err);
  })
}

const toAddClue = () => {
  router.push("/dashboard/clue/add")
}

const importDialogVisible = ref(false);
const importClue = () =>{
  importDialogVisible.value = true;
}
const uploadRef = ref<UploadInstance>();
const submitExcel = () => {
  uploadRef.value?.submit();
}
const uploadFile = async (e:any) => {
  let file = e.file;
  // console.log(file)
  const formData = new FormData();
  // 将文件添加到formData中，'file'是字段名，可以根据服务器要求更改
  formData.append('file', file);

  await post("/api/clue/importExcel",formData,{
    headers:{"Content-Type":"multipart/form-data"}
  }).then(res=>{
    if(res.data.code == 200){
      tips("导入成功",tipType.success);
      uploadRef.value?.clearFiles();
      getActivityClue()
      importDialogVisible.value = false;
    }else {
      tips("导入失败",tipType.error);
    }
  }).catch(err=>{
    tips("导入失败",tipType.error)
  })
}

const delClueBatch = () =>{
  console.log(selectedActivityClue.value)
}

const onToDetailClue = (clue:any) => {
  router.push("/dashboard/clue/detail/"+clue.clue.id)
}
const onUpdateClue = (clue:any) => {
  // console.log(clue.clue.id)
  router.push("/dashboard/clue/update/"+clue.clue.id)
}
const onDeleteClue = async (id:number) => {
  await del("/api/clue/"+id).then(res=>{
    if(res.data.code == 200){
      tips("删除成功",tipType.success);
      getActivityClue()
    }else {
      tips(res.data.msg,tipType.error);
    }
  }).catch(err=>{

  })

}

const selectedActivityClue = ref<number[]>([]);
const handleSelectionChange = (list : any[]) =>{
    let ids = list.map((item)=>{return item.clue.id})
    if(ids){
      console.log(ids)
      selectedActivityClue.value = ids;
    }
}
const delBatchAvailableComputed = computed(()=>{
  return selectedActivityClue.value.length<=0;
})
watch(()=>activityClueQuery.value.currentPage,()=>{
  getActivityClue()
})
onMounted(()=>{
  getActivityClue()
})
</script>


<template>
    <el-dialog v-model="importDialogVisible" title="导入线索Excel">
      <el-upload
          ref="uploadRef"
          method="post"
          :http-request="uploadFile"
          :auto-upload="false"
      >
        <template #trigger>
          <el-button :color="Color.SECONDARY">选择文件</el-button>
        </template>
        <span style="margin-left: 10px;">仅支持后缀名为.xlsx或.xls的文件</span>
        <template #tip>
          <div style="padding: 10px;">
            <div style="display: flex;align-items: center;">
              <img src="../../assets/warring.png" style="width: 15px;height: 15px;"/>
              <span style="margin-left: 10px;">导入提示:</span>
            </div>
            <ul class="upload-tip">
              <li>上传仅支持后缀名为.xlsx或.xls的文件</li>
              <li>给定的Excel文件第一行为表头，请勿删除</li>
              <li>请确认导入文件大小不超过50MB</li>
              <li>日期格式为：yyyy-MM-dd</li>
              <li>日期时间格式为：yyyy-MM-dd HH:mm:ss</li>
            </ul>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <el-button :color="Color.WARING" style="color: white" @click="()=>importDialogVisible=false">返回</el-button>
        <el-button :color="Color.POSITIVE" @click="submitExcel()">导入</el-button>
      </template>
    </el-dialog>

    <div style="margin:15px;">
      <el-button :color="Color.POSITIVE"  plain @click="importClue()" v-has-permission="'clue:import'">导入线索&nbsp;(Excel)</el-button>
      <el-button :color="Color.PRIMARY" plain @click="toAddClue()"    v-has-permission="'clue:add'" >添加线索</el-button>
      <el-button :color="Color.NEGATIVE" plain v-has-permission="'clue:delete'"
                 @click="delClueBatch()"
                 :disabled="delBatchAvailableComputed">批量删除</el-button>
    </div>

    <el-divider/>

    <el-table
        ref="multipleTableRef"
        :data="clueList"
        style="width: 100%"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column type="index"                         label="序号" width="55"/>
      <el-table-column property="owner.name"                label="负责人" width="120" />
      <el-table-column property="activity.name"             label="所属活动"  />
      <el-table-column property="clue.fullName"             label="姓名"  >
        <template #default="scope">
          <a  @click="onToDetailClue(scope.row)" style="color: #7640ff;text-decoration: underline;cursor: pointer">
            {{scope.row.clue.fullName}}
          </a>
        </template>
      </el-table-column>
<!--      <el-table-column property="clue.fullName"             label="姓名"  />-->
      <el-table-column property="appellation.typeValue"     label="称呼"  />
      <el-table-column property="clue.phone"                label="手机"  />
      <el-table-column property="clue.weixin"               label="微信"  />
      <el-table-column property="needLoan.typeValue"        label="是否贷款"  />
      <el-table-column property="intentionState.typeValue"  label="意向状态"  />
      <el-table-column property="intentionProduct.name"     label="意向产品"  />
<!--      <el-table-column property="state.typeValue"           label="线索状态"  />-->
      <el-table-column property="state.typeValue" label="线索状态">
        <template #default="scope">
          <el-tag :type="scope.row.state.id == -1 ? 'success' : 'primary'">
            {{scope.row.state.typeValue}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column property="source.typeValue"          label="线索来源"  />
      <el-table-column property="nextContactTime"           label="下次联系时间"  />

      <el-table-column label="操作" min-width="250px">
        <template #default="scope">
          <el-button  :color="Color.PRIMARY"              v-has-permission="'clue:view'"        @click="onToDetailClue(scope.row)">详情</el-button>
          <el-button  :color="Color.WARING" style="color:white;"   v-has-permission="'clue:edit'"     @click="onUpdateClue(scope.row)">修改</el-button>
          <el-button  :color="Color.NEGATIVE" v-has-permission="'clue:delete'"
                      @click="onDeleteClue(scope.row.clue.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination style="margin-top: 20px;margin-left: 20px;"
                   background
                   layout="prev, pager, next"
                   :total="total"
                   :page-size="10"
                   v-model:current-page="activityClueQuery.currentPage"
    />
</template>

<style scoped>
.upload-tip{
  margin-left: 20px;
  font-size:12px;
  display: flex;
  gap: 5px;
  flex-direction: column;
}
</style>