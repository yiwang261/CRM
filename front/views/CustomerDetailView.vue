<template>
  <!--线索详情-->
  <el-form
      :model="clueDetail"
      label-width="110px"
      style="max-width: 95%;">

    <el-form-item label="负责人">
      <el-select
          v-model="clueDetail.ownerId"
          placeholder="请选择负责人"
          style="width: 100%"
          clearable
          disabled>
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="所属活动">
      <el-select
          v-model="clueDetail.activityId"
          placeholder="请选择所属活动"
          style="width: 100%"
          disabled
          clearable>
        <el-option
            v-for="item in activityOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="姓名">
      <el-input v-model="clueDetail.fullName" disabled/>
    </el-form-item>

    <el-form-item label="称呼">
      <el-select
          v-model="clueDetail.appellation"
          placeholder="请选择称呼"
          style="width: 100%"
          disabled
          clearable>
        <el-option
            v-for="item in appellationOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="手机">
      <el-input v-model="clueDetail.phone" disabled/>
    </el-form-item>

    <el-form-item label="微信">
      <el-input v-model="clueDetail.weixin" disabled/>
    </el-form-item>

    <el-form-item label="QQ">
      <el-input v-model="clueDetail.qq" disabled/>
    </el-form-item>

    <el-form-item label="邮箱">
      <el-input v-model="clueDetail.email" disabled/>
    </el-form-item>

    <el-form-item label="年龄">
      <el-input v-model="clueDetail.age" disabled/>
    </el-form-item>

    <el-form-item label="职业">
      <el-input v-model="clueDetail.job" disabled/>
    </el-form-item>

    <el-form-item label="年收入">
      <el-input v-model="clueDetail.yearIncome" disabled/>
    </el-form-item>

    <el-form-item label="住址">
      <el-input v-model="clueDetail.address" disabled/>
    </el-form-item>

    <el-form-item label="贷款">
      <el-select
          v-model="clueDetail.needLoan"
          placeholder="请选择是否需要贷款"
          style="width: 100%"
          disabled
          clearable>
        <el-option
            v-for="item in needLoanOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="意向状态">
      <el-select
          v-model="clueDetail.intentionState"
          placeholder="请选择意向状态"
          style="width: 100%"
          disabled
          clearable>
        <el-option
            v-for="item in intentionStateOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="意向产品">
      <el-select
          v-model="clueDetail.intentionProduct"
          placeholder="请选择意向产品"
          style="width: 100%"
          disabled
          clearable>
        <el-option
            v-for="item in productOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索状态">
      <el-select
          v-model="clueDetail.state"
          placeholder="请选择线索状态"
          style="width: 100%"
          disabled
          clearable>
        <el-option
            v-for="item in clueStateOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索来源">
      <el-select
          v-model="clueDetail.source"
          placeholder="请选择线索来源"
          style="width: 100%"
          disabled
          clearable>
        <el-option
            v-for="item in sourceOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索描述">
      <el-input
          v-model="clueDetail.description"
          :rows="5"
          type="textarea"
          placeholder="请输入线索描述"
          disabled/>
    </el-form-item>

    <el-form-item label="下次联系时间">
      <el-date-picker
          v-model="clueDetail.nextContactTime"
          type="datetime"
          style="width: 100%;"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择下次联系时间"
          disabled/>
    </el-form-item>
  </el-form>

  <!--线索的历史跟踪记录-->
  <el-table
      :data="clueRemarkList"
      style="width: 100%">
    <el-table-column type="index" label="序号" width="100"/>
    <el-table-column property="noteWayDO.typeValue" label="跟踪方式"/>
    <el-table-column property="noteContent" label="跟踪内容"/>
    <el-table-column property="createTime" label="跟踪时间" width="175"/>
    <el-table-column property="createByDO.name" label="跟踪人" width="175"/>
    <el-table-column property="editTime" label="编辑时间" width="175"/>
    <el-table-column property="editByDO.name" label="编辑人" width="175"/>
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

  <!--客户详情-->
  <el-form
      ref="customerRemarkFormRef"
      :model="customerRemark"
      label-width="110px"
      :rules="customerRemarkRules"
      style="max-width: 95%;">

    <el-form-item label="意向产品">
      <el-select
          v-model="customerDetail.product"
          placeholder="请选择意向产品"
          style="width: 100%"
          clearable
          disabled>
        <el-option
            v-for="item in productOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="客户描述">
      <el-input
          v-model="customerDetail.description"
          :rows="5"
          type="textarea"
          placeholder="请输入线索描述"
          disabled/>
    </el-form-item>

    <el-form-item label="下次跟踪时间">
      <el-input v-model="customerDetail.nextContactTime" disabled/>
    </el-form-item>

    <!--填写跟踪记录-->
    <!--给客户创建交易-->
    <el-form-item label="填写跟踪记录" prop="noteContent">
      <el-input
          v-model="customerRemark.noteContent"
          :rows="8"
          type="textarea"/>
    </el-form-item>
    <el-form-item label="跟踪方式" prop="noteWay">
      <el-select
          v-model="customerRemark.noteWay"
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
      <el-button type="primary" @click="customerRemarkSubmit(customerRemarkFormRef)">提 交</el-button>
      <el-button type="success" @click="createTran">创建交易</el-button>
      <el-button type="success" plain @click="router.back()">返回</el-button>
    </el-form-item>
  </el-form>

  <!--客户的历史跟踪记录-->
  <el-table
      :data="customerRemarkList"
      style="width: 100%">
    <el-table-column type="index" label="序号" width="100"/>
    <el-table-column property="noteWayDO.typeValue" label="跟踪方式"/>
    <el-table-column property="noteContent" label="跟踪内容"/>
    <el-table-column property="createTime" label="跟踪时间" width="175"/>
    <el-table-column property="createByDO.name" label="跟踪人" width="175"/>
    <el-table-column property="editTime" label="编辑时间" width="175"/>
    <el-table-column property="editByDO.name" label="编辑人" width="175"/>
    <el-table-column label="操作" width="150">
      <template #default="scope">
        <a href="javascript:" @click="edit(scope.row.id)">编辑</a>
        &nbsp;
        <a href="javascript:" @click="del(scope.row.id)">删除</a>
      </template>
    </el-table-column>
  </el-table>
  <p>
    <el-pagination style="margin-top: 20px;margin-left: 20px;"
                   background
                   layout="prev, pager, next"
                   :total="total"
                   :page-size="10"
                   v-model:current-page="current"
    />
  </p>

<!--  给客户创建交易的弹窗（对话框）-->
  <el-dialog v-model="createTranDialogVisible" title="创建交易" width="55%" center>
    <el-form ref="createTranRefForm" :model="tranQuery" label-width="110px" :rules="createTranRules">

      <el-form-item label="交易金额" prop="money">
        <el-input v-model="tranQuery.money"/>
      </el-form-item>

      <el-form-item label="预计成交时间" prop="expectedDate">
        <el-date-picker
            v-model="tranQuery.expectedDate"
            type="datetime"
            style="width: 100%;"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择预计成交时间"/>
      </el-form-item>

      <el-form-item label="交易阶段" prop="stage">
        <el-select v-model="tranQuery.stage" placeholder="请选择交易阶段" style="width: 100%;">
          <el-option
              v-for="item in stageOptions"
              :key="item.id"
              :label="item.typeValue"
              :value="item.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="交易描述" prop="description">
        <el-input
            v-model="tranQuery.description"
            :rows="8"
            type="textarea"
            placeholder="请输入交易描述"/>
      </el-form-item>

      <el-form-item label="下次跟踪时间" prop="nextContactTime">
        <el-date-picker
            v-model="tranQuery.nextContactTime"
            type="datetime"
            style="width: 100%;"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择下次跟踪时间"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="createTranDialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="createTranSubmit(customerRemarkFormRef)">提 交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">

// import {router}from "@/router/index.ts";
import {get,post} from '@/api';
import {onMounted, ref} from "vue";
import {dicType, type option} from "@/interface/enum.js";
import type {optionType} from "@/interface/enum.js";
import type {FormInstance} from "element-plus";
import {tips, tipType} from "@/utils/tip";
import {reactive} from "vue";
import router from "@/router";
const props = defineProps(['id'])

const customerRemarkFormRef = ref<FormInstance>()
const  pageSize = ref(10);
const   total =  ref(0)
const current = ref(1)

const clueDetail = ref({
  ownerId: '',
  name: '',
  activityId: '',
  fullName:'',
  appellation:'',
  phone:'',
  weixin:'',
  qq:'',
  email:'',
  age:'',
  job:'',
  yearIncome:'',
  needLoan:'',
  address:'',
  intentionState:'',
  intentionProduct:'',
  state:'',
  source:'',
  description:'',
  nextContactTime:'',
})

const clueRemarkList = ref([{
  createByDO : {},
  editByDO : {},
  noteWayDO : {}
}])
const tranQuery = ref({
  money:'',
  expectedDate:'',
  stage:'',
  description:'',
  nextContactTime:'',

})
const createTranDialogVisible = ref(false)

const customerRemarkRules = reactive({
  money : [
    { required: true, message: '请输入交易金额', trigger: 'blur' },
    { pattern : /^[0-9]+(\.[0-9]{2})?$/, message: '交易金额必须是整数或者两位小数', trigger: 'blur'}
  ],
  expectedDate : [
    { required: true, message: '请选择预计成交时间', trigger: 'blur' },
  ],
  stage : [
    { required: true, message: '请选择交易阶段', trigger: 'blur' },
  ],
  description : [
    { required: true, message: '活动描述不能为空', trigger: 'blur' },
    { min: 5, max: 255, message: '活动名称长度范围为5-255个字符', trigger: 'blur' }
  ],
  nextContactTime : [
    { required: true, message: '请选择下次跟踪时间', trigger: 'blur' },
  ]
})

const customerRemarkList = ref( [{
  createByDO : {},
  editByDO : {},
  noteWayDO : {}
}])
const customerDetail = ref({
  product:'',
  description:'',
  nextContactTime:'',
  id:'',
  clueId:0
})

const edit = (id:number) => {

}
const del = (id:number) => {
}

const customerRemark = ref({
  noteContent:'',
  noteWay:'',
})
const clueId = ref(0)
const noteWayOptions = ref<[option]>();
//市场活动的下拉选项数据，初始值是空
const activityOptions = ref<[optionType]>()
const ownerOptions = ref<[optionType]>()
//意向产品的下拉选项
const productOptions =ref<[optionType]>()
const appellationOptions = ref<[option]>()
const needLoanOptions = ref<[option]>()
const intentionStateOptions = ref<[option]>()
const clueStateOptions = ref<[option]>()
const sourceOptions = ref<[option]>()
const stageOptions = ref<[option]>()

const loadClueDetail = async () => {
  await get("/api/clue/" + clueId.value, {}).then(resp => {
    if (resp.data.code === 200) {
      clueDetail.value = resp.data.data;
    }
  })
}

const  loadClueRemarkList = async ()=>{
  await  get("/api/clue/" + clueId.value + "/remark", {
    current : current.value
  }).then(resp => {
    if (resp.data.code === 200) {
      // console.log(resp.data.data)
      clueRemarkList.value = resp.data.data.list; // resp.data  =  R 对象
      total.value = resp.data.data.total;
    }
  })
}
const loadCustomerRemarkList = async ()=>{
  await get("/api/customer/" + customerDetail.value.id + "/remark", {
    current : current.value
  }).then(resp => {
    if (resp.data.code === 200) {
      customerRemarkList.value = resp.data.data.list; // resp.data  =  R 对象
      total.value = resp.data.data.total;
    }
  })
}
const loadCustomerDetail = async () => {
  await get("/api/customer/" + props.id).then(async (resp) => {
    if (resp.data.code === 200) {
      // console.log(resp.data.data)
      customerDetail.value = resp.data.data;
      clueId.value = customerDetail.value.clueId;
      await loadClueDetail();
      await loadClueRemarkList();
      await  loadCustomerRemarkList();
    }
  }).catch((err) => {
    tips(err, tipType.error);
  })
}
const createTranRules =reactive({
  money : [
    { required: true, message: '请输入交易金额', trigger: 'blur' },
    { pattern : /^[0-9]+(\.[0-9]{2})?$/, message: '交易金额必须是整数或者两位小数', trigger: 'blur'}
  ],
  expectedDate : [
    { required: true, message: '请选择预计成交时间', trigger: 'blur' },
  ],
  stage : [
    { required: true, message: '请选择交易阶段', trigger: 'blur' },
  ],
  description : [
    { required: true, message: '活动描述不能为空', trigger: 'blur' },
    { min: 5, max: 255, message: '活动名称长度范围为5-255个字符', trigger: 'blur' }
  ],
  nextContactTime : [
    { required: true, message: '请选择下次跟踪时间', trigger: 'blur' },
  ]
})
const customerRemarkSubmit = (formRef:FormInstance|undefined) => {
  if (!formRef) return;
  formRef.validate(async valid => {
    if(!valid) return;
    post("/api/customer/remark", {
      customerId : customerDetail.value.id,
      noteWay : customerRemark.value.noteWay,
      noteContent : customerRemark.value.noteContent
    }).then(resp => {
      if (resp.data.code === 200) {
        //提交客户跟踪记录成功，提示一下
        tips("提交客户记录成功", tipType.success);
        loadCustomerRemarkList();
      } else {
        //提交客户记录失败，提示一下
        tips("提交客户记录失败", tipType.error);
      }
    })

  })
}

const createTran = () => {
  createTranDialogVisible.value = true;

}
const getOwner = async () => {
  try {
    const res = await get("/api/owner")
    if(res.data.code === 200){
      ownerOptions.value = res.data.data;
    }
  }catch (e) {

  }
}
const createTranSubmit = async (formRef:FormInstance|undefined) => {
  if(!formRef) return;
  await formRef.validate(async(valid) => {
    if(!valid) return;
     post("/api/customer/tran", {
      customerId : customerDetail.value.id,
      ...tranQuery.value
      // money : tranQuery.value.money,
      // expectedDate : tranQuery.value.expectedDate,
      // stage : tranQuery.value.stage,
      // description : tranQuery.value.description,
      // nextContactTime : tranQuery.value.nextContactTime
    }).then(resp => {
      if (resp.data.code === 200) {
        //创建交易成功，提示一下
        tips("创建交易成功", tipType.success);
        createTranDialogVisible.value = false;
      }
    }).catch(err => {
      tips(err, tipType.error);
     })
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
    // tips(err.message,tipType.error)
  })

}
onMounted(async () =>{
  await loadCustomerDetail();
  // await loadClueDetail();
  await getOwner();
  await getType(dicType.stage);
  await getType(dicType.activity);
  await getType(dicType.appellation);
  await getType(dicType.needLoan);
  await getType(dicType.intentionState);
  await getType(dicType.clueState);
  await getType(dicType.source);
  await getType(dicType.product);
  await getType(dicType.noteWay);
})

</script>

<style scoped>

</style>