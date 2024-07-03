<script setup lang="ts">

import {dicType, modeType, type option, type optionType} from "@/interface/enum";
import router from "@/router";
import {onMounted, ref, watch} from "vue";
import type {FormInstance, FormRules} from "element-plus";
import {del, get, post, put} from "@/api";
import {tips, tipType} from "@/utils/tip";
import type {ClueRemark, clueRemarkQuery} from "@/interface/clueRemark";
import {Color} from "@/assets/color";
const props = defineProps(
    {id: String}
)
const clueFormRef =  ref<FormInstance>();
const clueForm = ref({
  id: 0,
  ownerId: -1,
  name: '',
  activityId:'',
  appellation: '',
  fullName: '',
  phone: '',
  weixin: '',
  qq: '',
  email: '',
  age: '',
  job: '',
  yearIncome: '',
  address: '',
  needLoan: '',
  intentionState: '',
  intentionProduct: '',
  state: undefined,
  source: '',
  description: '',
  nextContactTime: '',
});

// 备注表单操作
const clueRemarkRef = ref<FormInstance>();
const clueRemarkRules = ref<FormRules<ClueRemark>>({
  noteContent: [
    {required: true, message: '请输入备注内容', trigger: 'blur'}
  ],
  noteWay: [
      {required: true, message: '请选择备注方式', trigger: 'blur'}
  ]

})
const clueRemarkForm = ref<ClueRemark>({
  clueId: 0,
  noteWay: 61,
  noteContent: '',
})
const submitClueRemark =  (clueRemarkRef: FormInstance|undefined) => {
  if(!clueRemarkRef)return;
   clueRemarkRef.validate(async (valid) => {
    if(!valid)return;
        if( typeof props.id === 'string')
          clueRemarkForm.value.clueId = Number.parseInt(props.id);
        await addClueRemark();
        await getClueRemarkList();
    clueRemarkRef.resetFields();
  })
}
const addClueRemark = async () => {
  await post("/api/clue/remark",clueRemarkForm.value).then(res=>{
    if(res.data.code === 200){
      tips(res.data.msg,tipType.success);
    }else{
      tips(res.data.msg,tipType.error);
    }
  }).catch(err=>{
    tips(err.message,tipType.error);
  })
}

//备注列表
const clueRemarkVO = ref({
  total:0,
  list:[]
})
const clueRemarkQuery = ref<clueRemarkQuery>({
  clueId: 0,
  current: 1,
  size: 4,
})
watch(()=>clueRemarkQuery.value.current,()=>{
  getClueRemarkList();
})
const getClueRemarkList = async () => {
  if( typeof props.id === 'string')
  clueRemarkQuery.value.clueId = Number.parseInt(props.id);
  try {
      await get("/api/clue/remark", {
        current: clueRemarkQuery.value.current,
        size: clueRemarkQuery.value.size,
        clueId: clueRemarkQuery.value.clueId,
      }).then(res=>{
        if(res.data.code === 200){
          clueRemarkVO.value.total = res.data.data.total;
          clueRemarkVO.value.list = res.data.data.list;
          // console.log(res.data.data)
        }
      })
  }catch (e) {

  }
}

const clueRemarkDialogRef = ref<FormInstance>();
const clueRemarkDialogVisible = ref<boolean>(false);
const clueRemarkDialogForm = ref<ClueRemark>({
  id: 0,
  noteContent: '',
})
const clueRemarkDialogRules = ref<FormRules<ClueRemark>>({
  noteContent: [
    {required: true, message: '请输入备注内容', trigger: 'blur'},
    {min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur'}
  ],
})
const submitUpdate = (formRef:FormInstance|undefined)=>{
  if(!formRef) return;
  formRef.validate(async (valid) => {
    if(!valid)return;

    await updateClueRemark();
    await getClueRemarkList();
    cancel();
  })
}
const deleteClueRemark = async (id:number) => {
  await del ("/api/clue/remark/"+id).then(res=>{
    if(res.data.code === 200){
      tips(res.data.msg,tipType.success);
      getClueRemarkList();
    }else{
      tips(res.data.msg,tipType.error);
    }
  }).catch(error=>{

  })
}
const updateClueRemark  = async () => {
  await put("/api/clue/remark",clueRemarkDialogForm.value).then(res=>{
    if(res.data.code === 200){
      tips(res.data.msg,tipType.success);
    }else{
      tips(res.data.msg,tipType.error);
    }
  }).catch(error=>{

  })
}
const openUpdateDialog = async (id:number) => {
  clueRemarkDialogForm.value.id = id;
  clueRemarkDialogVisible.value = true;
}
const cancel = ()=>{
  clueRemarkDialogVisible.value = false;
  clueRemarkDialogRef.value?.resetFields();
}



// 客户转化
const convertCustomerDialogVisible = ref<boolean>(false);
const convertCustomerRef = ref<FormInstance>();
const convertCustomerForm = ref({
  product:'',
  nextContactTime:'',
  description:'',
})
const submitConvertCustomer = async (convertCustomerRef:FormInstance|undefined) => {
  if(!convertCustomerRef) return;
  await convertCustomerRef.validate(async (valid) => {
    if(!valid)return;

    post("/api/customer", {
      clueId: clueForm.value.id,
      ...convertCustomerForm.value
    }).then(res=>{
      if (res.data.code === 200){
        tips(res.data.msg,tipType.success);
        cancelCustomerDialog();
      }else {
        tips(res.data.msg,tipType.error);
      }
    }).catch(err=>{

    })
  })
}
const convertCustomerFormRules = ref<FormRules<any>>({
  product:[
      {required: true, message: '请选择产品', trigger: 'blur'}
  ],
  nextContactTime:[
      {required: true, message: '请选择下次联系时间', trigger: 'blur'}
  ],
  description:[
      {required: true, message: '请输入备注', trigger: 'blur'},
      {min: 1, max: 250, message: '长度在 1 到 250 个字符', trigger: 'blur'}
  ]
})
const cancelCustomerDialog = ()=>{
  convertCustomerDialogVisible.value = false;
  convertCustomerRef.value?.resetFields();
}



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

//获取所有负责人
const ownerOptions = ref<[optionType]>();
const getOwner = async () => {
  try {
    const res = await get("/api/owner",{})
    if(res.data.code === 200){
      ownerOptions.value = res.data.data;
    }

  }catch (e) {

  }
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
        default:{
          tips("没有该类型",tipType.error)
        }
      }
    }
  }).catch(err=>{
    // tips(err.message,tipType.error)
  })

}

const loadLoginUser = async () => {
  try{
    const resp = await get("/api/login/info")
    if(resp.data.code !== 200){
      tips("加载失败",tipType.error)
      return
    }
    clueForm.value.ownerId= resp.data.data.id
  }catch (e) {

  }

}
const  loadClue =async ()=>{
  let id = props.id;
  await get("/api/clue/"+id).then(res=>{
    if(res.data.code === 200){
      clueForm.value = res.data.data;
    }
  }).catch(err=>{

  })
}
onMounted(async () =>{
  await getOwner();
  await loadLoginUser();
  await getType(dicType.activity);
  await getType(dicType.appellation);
  await getType(dicType.needLoan);
  await getType(dicType.intentionState);
  await getType(dicType.clueState);
  await getType(dicType.source);
  await getType(dicType.product);
  await getType(dicType.noteWay);
  await loadClue();
  await getClueRemarkList();
})

</script>


<template>
  <el-row  >
    <h1 style="margin: 15px 15px 15px 25%;">线索详情</h1>
  </el-row><!--:rules="clueFormRules"-->
  <el-form ref="clueFormRef" :model="clueForm"  disabled>
    <el-row>
      <el-col :span="6" >
        <el-form-item label-width="100px" label="负责人" prop="name" >
          <el-select v-model="clueForm.ownerId"
                     :disabled="true"
                     placeholder="请选择负责人">
            <el-option
                v-for="item in ownerOptions"
                :key="item.id"
                :value="item.id"
                :label="item.name"
            />
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="6" :offset="1">
        <el-form-item label="所属活动" label-width="100px">
          <el-select
              v-model="clueForm.activityId"
              placeholder="请选择所属活动"
              style="width: 100%"
              clearable>
            <el-option
                v-for="item in activityOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="6">
        <el-form-item label="姓名" prop="fullName" label-width="100px">
          <el-input v-model="clueForm.fullName" />
        </el-form-item>
      </el-col>

      <el-col :span="6" :offset="1">
        <el-form-item label="称呼" label-width="100px">
          <el-select
              v-model="clueForm.appellation"
              placeholder="请选择称呼"
              style="width: 100%"
              clearable>
            <el-option
                v-for="item in appellationOptions"
                :key="item.id"
                :label="item.typeValue"
                :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-col>

    </el-row>

    <el-row>
      <el-col :span="6">
        <el-form-item label="手机" prop="phone" label-width="100px" ><!--此时是录入-->
          <el-input v-model="clueForm.phone" disabled/><!--:readonly=""-->
        </el-form-item>
      </el-col>

      <el-col :span="6" :offset="1" >
        <el-form-item label="微信" label-width="100px" prop="weixin">
          <el-input v-model="clueForm.weixin"/>
        </el-form-item>
      </el-col>
    </el-row>



    <el-row>
      <el-col :span="6">

        <el-form-item label="QQ" prop="qq" label-width="100">
          <el-input v-model="clueForm.qq"/>
        </el-form-item>
      </el-col>

      <el-col :span="6" :offset="1">

        <el-form-item label="邮箱" prop="email"  label-width="100">
          <el-input v-model="clueForm.email"/>
        </el-form-item>
      </el-col>
    </el-row>


    <el-row>
      <el-col :span="6">
        <el-form-item label="年龄" prop="age"  label-width="100">
          <el-input v-model="clueForm.age"/>
        </el-form-item>
      </el-col>

      <el-col :span="6" :offset="1">
        <el-form-item label="职业"  label-width="100" prop="job">
          <el-input v-model="clueForm.job"/>
        </el-form-item>
      </el-col>
    </el-row>


    <el-row>
      <el-col :span="6">
        <el-form-item label="年收入" prop="yearIncome"  label-width="100">
          <el-input v-model="clueForm.yearIncome"/>
        </el-form-item>
      </el-col>

      <el-col :span="6" :offset="1">
        <el-form-item label="住址"  label-width="100" prop="address">
          <el-input v-model="clueForm.address"/>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="6">

        <el-form-item label="贷款"  label-width="100">
          <el-select
              v-model="clueForm.needLoan"
              placeholder="请选择是否需要贷款"
              style="width: 100%"
              clearable>
            <el-option
                v-for="item in needLoanOptions"
                :key="item.id"
                :label="item.typeValue"
                :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="6" :offset="1">

        <el-form-item label="意向状态"  label-width="100">
          <el-select
              v-model="clueForm.intentionState"
              placeholder="请选择意向状态"
              style="width: 100%"
              clearable>
            <el-option
                v-for="item in intentionStateOptions"
                :key="item.id"
                :label="item.typeValue"
                :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>




    <el-row>
      <el-col :span="6">
        <el-form-item label="意向产品"  label-width="100">
          <el-select
              v-model="clueForm.intentionProduct"
              placeholder="请选择意向产品"
              style="width: 100%"
              clearable>
            <el-option
                v-for="item in productOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="6" :offset="1">
        <el-form-item label="线索状态"  label-width="100">
          <el-select
              v-model="clueForm.state"
              placeholder="请选择线索状态"
              style="width: 100%"
              clearable>
            <el-option
                v-for="item in clueStateOptions"
                :key="item.id"
                :label="item.typeValue"
                :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="6">
        <el-form-item label="线索来源"  label-width="100">
          <el-select
              v-model="clueForm.source"
              placeholder="请选择线索来源"
              style="width: 100%"
              clearable>
            <el-option
                v-for="item in sourceOptions"
                :key="item.id"
                :label="item.typeValue"
                :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="6" :offset="1">
        <el-form-item label="下次联系时间"  label-width="100">
          <el-date-picker
              v-model="clueForm.nextContactTime"
              type="datetime"
              style="width: 100%;"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="请选择下次联系时间"/>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row >
      <el-col :span="13">
        <el-form-item label="线索描述" prop="description"  label-width="100">
          <el-input
              v-model="clueForm.description"
              :rows="2"
              type="textarea"
              placeholder="请输入线索描述"/>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>


<!--  线索备注表单-->
  <el-form ref="clueRemarkRef" :model="clueRemarkForm" :rules="clueRemarkRules">
    <el-row>
      <el-col :span="13">
        <el-form-item label="备注" prop="noteContent"  label-width="100">
          <el-input
              v-model="clueRemarkForm.noteContent"
              :rows="4"
              type="textarea"
              placeholder="请输入备注"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="13">
        <el-form-item label="联系方式" prop="noteWay"  label-width="100">
          <el-select
              placeholder="请选择联系方式"
              style="width: 100%"
              clearable
              v-model="clueRemarkForm.noteWay">
          <el-option
              v-for="item in noteWayOptions"
              :key="item.id"
              :label="item.typeValue"
              :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row justify="center" style="margin-right: 200px">
      <el-col :span="10">
        <el-button  :color="Color.PRIMARY" @click="submitClueRemark(clueRemarkRef)">提 交</el-button>
        <el-button  :color="Color.POSITIVE" plain @click="router.back()">返 回</el-button>
        <el-button :color="Color.SECONDARY" @click="()=>{convertCustomerDialogVisible= true}" v-if="clueForm.state !== -1">客户转化</el-button>
      </el-col>
    </el-row>
  </el-form>
  <el-divider/>
  <el-table :data="clueRemarkVO.list" style="width: 100%">
    <el-table-column type="index"                                       width="50" />
    <el-table-column prop="noteWayName"      label="联系方式" width="180" />
    <el-table-column prop="noteContent"  label="跟踪内容" width="180" />
    <el-table-column prop="createTime"   label="备注时间" width="180" />
    <el-table-column prop="createByName"                label="备注人"   width="180" />
    <el-table-column prop="editTime"     label="编辑时间" />
    <el-table-column prop="editByName"                  label="编辑人" />

    <el-table-column label="操作">
      <template #default="scoped">
        <el-button type="warning" round @click="openUpdateDialog(scoped.row.id)">编辑</el-button>
        <el-button type="danger"  round @click="deleteClueRemark(scoped.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination style="margin-top: 20px;margin-left: 20px;"
                 background
                 layout="prev, pager, next"
                 :total="clueRemarkVO.total"
                 :page-size="clueRemarkQuery.size"
                 v-model:current-page="clueRemarkQuery.current"
  />


  <el-dialog title="客户转化" v-model="convertCustomerDialogVisible" width="60%" style="height: 55%;" :draggable="true">
    <el-form ref="convertCustomerRef" :model="convertCustomerForm" :rules="convertCustomerFormRules">
      <el-form-item label-width="90px" label="意向产品:" prop="product">
        <el-select
            v-model="convertCustomerForm.product"
            placeholder="请选择意向产品">
          <el-option
            v-for="item in productOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
        </el-select>
      </el-form-item >

      <el-form-item label-width="90px" label="客户描述:" prop="description">
          <el-input
            v-model="convertCustomerForm.description"
            :rows="10"
            type="textarea"
            placeholder="请输入客户描述"
          />
      </el-form-item>

      <el-form-item  label-width="90px" label="开始时间:" prop="startTime">
        <el-date-picker
            v-model="convertCustomerForm.nextContactTime"
            type="datetime"
            placeholder="前选择创建时间"
            value-format="YYYY-MM-DD HH:mm:ss"
            format="YYYY-MM-DD HH:mm:ss"
            date-format="YYYY/MM/DD ddd"
            time-format="A hh:mm:ss"
            style="width:250px; "
        />
      </el-form-item>

    </el-form>

    <template #footer>
      <el-button plain :color="Color.SECONDARY"   @click="cancelCustomerDialog()">取消</el-button>
      <el-button :color="Color.PRIMARY"  @click="submitConvertCustomer(convertCustomerRef)">转换</el-button>
    </template>
  </el-dialog>
<!--  编辑线索备注对话框-->
  <el-dialog title="查看跟踪记录详情" v-model="clueRemarkDialogVisible" width="50%" style="height: 45%;" :draggable="true">
    <el-form ref="clueRemarkDialogRef" :model="clueRemarkDialogForm" :rules="clueRemarkDialogRules">
      <el-form-item   prop="noteContent">
        <el-input
            v-model="clueRemarkDialogForm.noteContent"
            :rows="10"
            type="textarea"
            placeholder="请输入备注详情"
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="cancel()">取消</el-button>
      <el-button type="primary"  @click="submitUpdate(clueRemarkDialogRef)">确定</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>