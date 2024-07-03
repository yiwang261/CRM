<template>
  <el-form ref="tranRefForm" :model="tranRemark" label-width="120px" :rules="tranRemarkRules">
    <el-form-item label="ID：">
      <!--<el-input v-model="tranDetail.id" disabled/>-->
      <el-tag type="primary">{{ tranDetail.id }}</el-tag>
<!--      <div  :style="{ background: Color.PRIMARY, width: '100%' }"></div>-->
    </el-form-item>

    <el-form-item label="交易流水号：">
      <!--<el-input v-model="tranDetail.tranNo" disabled/>-->
      {{ tranDetail.tranNo }}
    </el-form-item>

    <el-form-item label="交易金额">
      {{ tranDetail.money }}
    </el-form-item>

    <el-form-item label="预计成交时间">
      {{ tranDetail.expectedDate }}
    </el-form-item>

    <el-form-item label="交易阶段">

      <el-select v-model="tranDetail.stage" placeholder="请选择交易阶段" style="width: 100%;" disabled>
        <el-option
            v-for="item in stageOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="交易描述">
      <el-input
          v-model="tranDetail.description"
          :rows="5"
          type="textarea"
          disabled/>
    </el-form-item>

    <el-form-item label="下次跟踪时间">
      <el-input v-model="tranDetail.nextContactTime" disabled/>
    </el-form-item>

    <el-form-item label="创建时间">
      <el-input v-model="tranDetail.createTime" disabled/>
    </el-form-item>

    <el-form-item label="创建人">
      <el-input v-model="tranDetail.createByDO.name" disabled/>
    </el-form-item>

    <el-form-item label="编辑时间">
      <el-input v-model="tranDetail.editTime" disabled/>
    </el-form-item>

    <el-form-item label="编辑人">
      <el-input v-model="tranDetail.editByDO.name" disabled/>
    </el-form-item>

    <el-form-item>
      <el-table
          :data="tranRemarkList"
          style="width: 100%">
        <el-table-column type="index" label="序号" width="100"/>
        <el-table-column property="noteWayDO.typeValue" label="跟踪方式"/>
        <el-table-column property="noteContent" label="跟踪内容"/>
        <el-table-column property="createTime" label="跟踪时间" width="175"/>
        <el-table-column property="createByDO.name" label="跟踪人" width="175"/>
        <el-table-column property="editTime" label="编辑时间" width="175"/>
        <el-table-column property="editByDO.name" label="编辑人" width="175"/>
<!--        <el-table-column label="操作" width="150">-->
<!--          <template #default="scope">-->
<!--            <a href="javascript:" :style="{color:Color.WARING}" @click="edit(scope.row.id)">编辑</a>-->
<!--            &nbsp;-->
<!--            <a href="javascript:" :style="{color:Color.NEGATIVE}"  @click="del(scope.row.id)">删除</a>-->
<!--          </template>-->
<!--        </el-table-column>-->
      </el-table>
      <p>
        <el-pagination style="margin-top: 20px;margin-left: 20px;"
                       background
                       layout="prev, pager, next"
                       :total="total"
                       :page-size="pageSize"
                       v-model:current-page="current"
        />
      </p>
    </el-form-item>

    <el-form-item label="填写跟踪记录" prop="noteContent">
      <el-input
          v-model="tranRemark.noteContent"
          :rows="8"
          type="textarea"/>
    </el-form-item>
    <el-form-item label="跟踪方式" prop="noteWay">
      <el-select
          v-model="tranRemark.noteWay"
          placeholder="请选择跟踪方式"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in noteWayOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button :color="Color.PRIMARY" @click="tranRemarkSubmit(tranRefForm)">提 交</el-button>
      <el-button :color="Color.WARING" style="color: white" @click="updateTranStage">更新交易阶段</el-button>
      <el-button :color="Color.POSITIVE" plain @click="$router.back()">返 回</el-button>
    </el-form-item>

    <el-form-item>
      <el-table
          :data="tranHistoryList"
          style="width: 100%">
        <el-table-column type="index" label="序号" width="100"/>
        <el-table-column property="stageDO.typeValue" label="交易阶段" width="100"/>
        <el-table-column property="money" label="交易金额"/>
        <el-table-column property="expectedDate" label="预计成交时间"/>
        <el-table-column property="createTime" label="创建时间"/>
        <el-table-column property="createByDO.name" label="创建人"/>
      </el-table>
    </el-form-item>
  </el-form>

  <!--更新交易阶段的弹窗（对话框）-->
  <el-dialog v-model="tranHistoryDialogVisible" title="更新交易阶段" width="55%" center>
    <el-form ref="tranHistoryRefForm" :model="tranHistoryQuery" label-width="110px" :rules="tranHistoryRules">
      <el-form-item label="交易阶段" prop="stage">
        <el-select v-model="tranHistoryQuery.stage" placeholder="请选择交易阶段" style="width: 100%;">
          <el-option
              v-for="item in stageOptions"
              :key="item.id"
              :label="item.typeValue"
              :value="item.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="交易金额" prop="money">
        <el-input v-model="tranHistoryQuery.money"/>
      </el-form-item>




      <el-form-item label="预计成交时间" prop="expectedDate">
        <el-date-picker
            v-model="tranHistoryQuery.expectedDate"
            type="datetime"
            style="width: 100%;"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择预计成交时间"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button  :color="Color.POSITIVE" plain @click="tranHistoryDialogVisible = false">关 闭</el-button>
        <el-button :color="Color.PRIMARY" @click="tranHistorySubmit(tranHistoryRefForm)">提 交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {get,post} from '@/api'
import {onMounted, reactive, ref} from "vue";
import {tips, tipType} from "@/utils/tip";
import type {FormInstance} from "element-plus";
import {dicType, type option, type optionType} from "@/interface/enum";
import router from "@/router";
import {Color} from "@/assets/color";
const props = defineProps({
  id: {
    type: String,
    default: ''
  }
})

const current = ref(1)
const tranHistoryRefForm = ref<FormInstance>()
const tranDetail =ref( {
  id:'',
  tranNo:'',
  tranName:'',
  money:'',
  expectedDate:'',
  stage: 0,
  description:'',
  nextContactTime:'',
  createTime:'',
  createByDO:{
    name:''
  },
  editTime:'',
  editByDO:{
    name:''
  }
})
const tranRemark = ref({
  noteContent:'',
  noteWay:'',

})
const  tranRemarkRules = reactive ({
  noteContent : [
    { required: true, message: '跟踪内容不能为空', trigger: 'blur' },
    { min: 5, max: 255, message: '跟踪内容长度为5-255个字符', trigger: 'blur' }
  ],
  noteWay : [
    { required: true, message: '请选择跟踪方式', trigger: ['blur', 'change'] }
  ]
})

const tranHistoryRules = reactive( {
  stage : [
    { required: true, message: '请选择交易阶段', trigger: ['blur', 'change'] }
  ],
  money : [
    { required: true, message: '请输入交易金额', trigger: 'blur' },
    { pattern : /^[0-9]+(\.[0-9]{2})?$/, message: '交易金额必须是整数或者两位小数', trigger: 'blur'}
  ],
  expectedDate : [
    { required: true, message: '请选择预计成交时间', trigger: ['blur', 'change'] }
  ]
})
const tranHistoryDialogVisible = ref(false)

const tranHistoryQuery = ref({
  stage : '',
  money : '',
  expectedDate : '',
})

const tranRefForm = ref<FormInstance>()
const tranRemarkList =ref( [{
  noteWayDO : {},
  createByDO: {},
  editByDO : {}
}])

const pageSize = ref(10)
const total = ref(0)
const tranHistoryList = ref( [{
  stageDO : {},
  createByDO : {}
}])
const noteWayOptions = ref<[option]>();
//市场活动的下拉选项数据，初始值是空
const activityOptions = ref<[optionType]>()
//意向产品的下拉选项
const productOptions =ref<[optionType]>()
const appellationOptions = ref<[option]>()
const needLoanOptions = ref<[option]>()
const intentionStateOptions = ref<[option]>()
const clueStateOptions = ref<[option]>()
const sourceOptions = ref<[option]>()
const stageOptions = ref<[option]>()


const loadTranDetail = async ()=> {
  // let tranId = 0;
  // if( typeof props.id === 'string'){
  //   tranId = Number.parseInt(props.id);
  // }
  await get("/api/tran/" + props.id, {}).then(resp => {
    if (resp.data.code === 200) {
      tranDetail.value = resp.data.data;
    }
  })
}

const edit = (id:number)=> {
  //获取市场活动下拉列表数据
  console.log(id)
}
const del = (id:number)=> {
  //删除
  console.log(id)

}
const tranRemarkSubmit = async (tranRefFormRef:FormInstance|undefined) => {
  if (!tranRefFormRef) return;
  await tranRefFormRef.validate(async (isValid) => {
    if (isValid) {
     await post("/api/tran/remark", {
        tranId:tranDetail.value.id,
        noteWay: tranRemark.value.noteWay,
        noteContent: tranRemark.value.noteContent
      }).then(resp => {
        if (resp.data.code === 200) {
          //提交交易跟踪记录成功，提示一下
          tips("提交交易记录成功", tipType.success);
          //刷新一下页面
          loadTranRemarkList();
        } else {
          //提交交易记录失败，提示一下
          tips("提交交易记录失败，原因：" + resp.data.msg, tipType.error);
        }
      })
    }
  })
}
 const updateTranStage = ()=> {
    tranHistoryDialogVisible.value = true;
  }

  //更新交易阶段（提交保存）
const tranHistorySubmit =async (tranHistoryRefFormRef:FormInstance|undefined)=> {
  if (!tranHistoryRefFormRef) return;
  await  tranHistoryRefFormRef.validate(async (isValid) => {
      if (isValid) {
       await post("/api/tran/history", {
          tranId : tranDetail.value.id,
          stage : tranHistoryQuery.value.stage,
          money : tranHistoryQuery.value.money,
          expectedDate : tranHistoryQuery.value.expectedDate
        }).then(resp => {
          if (resp.data.code === 200) {
            //更新交易阶段成功，提示一下
            tips("更新交易阶段成功", tipType.success);
            loadTranHistoryList();
            tranHistoryDialogVisible.value = false;
            //刷新一下页面
          } else {
            //更新交易阶段失败，提示一下
            tips("更新交易阶段失败，原因：" + resp.data.msg, tipType.error);
          }
        })
      }
    })
  }

  //分页加载交易跟踪记录列表信息
 const loadTranRemarkList= async () => {
    // /api/tran/3/remark
   let tranId = 0;
   if( typeof props.id === 'string')
     tranId = Number.parseInt(props.id);
    await get("/api/tran/" + tranId + "/remark", {
      current : current.value
    }).then(resp => {
      if (resp.data.code === 200) {
        // console.log(resp.data.data)
        tranRemarkList.value = resp.data.data.list; // resp.data  =  R 对象
        pageSize.value = resp.data.data.pageSize;
        total.value = resp.data.data.total;
      }
    })
  }

  //加载交易的历史阶段记录
const loadTranHistoryList=async ()=> {
    // /api/tran/3/remark
    let tranId = router.currentRoute.value.params.id;
   await get("/api/tran/" + tranId + "/history", {}).then(resp => {
      if (resp.data.code === 200) {
        // console.log(resp.data.data)
        tranHistoryList.value = resp.data.data; // resp.data  =  R 对象
      }
    })
  }

const getType = async (type:dicType) => {
  await get("/api/dic/"+type,).then(res=>{
    if(res.data.code === 200){
      // console.log(res.data.data)
      switch (type) {
        case dicType.activity:{
          activityOptions.value = res.data.data;
          break;
        }
        case dicType.appellation:{
          appellationOptions.value = res.data.data;
          break;
        }
        case dicType.needLoan:{
          needLoanOptions.value = res.data.data;
          break;
        }
        case dicType.intentionState:{
          intentionStateOptions.value=res.data.data;
          break;
        }
        case dicType.clueState:{
          clueStateOptions.value = res.data.data;
          break;
        }
        case dicType.source:{
          sourceOptions.value = res.data.data;
          break;
        }
        case dicType.product:{
          productOptions.value = res.data.data;
          break;
        }
        case dicType.noteWay:{
          noteWayOptions.value = res.data.data;
          break;
        }
        case dicType.stage:{
          stageOptions.value = res.data.data;
          break;
        }
        default:{
          tips("没有该类型",tipType.error)
        }
      }
    }
  }).catch(err=>{
    tips(err.message,tipType.error)
  })

}
onMounted(async ()=>{
  await loadTranDetail();
  await getType(dicType.noteWay);
  await getType(dicType.stage);
  await loadTranRemarkList();
  await loadTranHistoryList();
})

</script>

<style scoped>

</style>