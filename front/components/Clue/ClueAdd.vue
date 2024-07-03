<template>
  <el-row  >
    <h1 style="margin: 15px 15px 15px 25%;">{{ title }}</h1>
  </el-row>
  <el-form ref="clueFormRef" :model="clueForm" :rules="clueFormRules">
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
          <el-input v-model="clueForm.phone" :disabled="mode===modeType.update"/><!--:readonly=""-->
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
              :rows="5"
              type="textarea"
              placeholder="请输入线索描述"/>
        </el-form-item>
      </el-col>


    </el-row>

    <el-row justify="center" style="margin-right: 200px">
      <el-col :span="10">
        <el-form-item>
          <el-button type="primary" @click="addClueSubmit(clueFormRef)">提 交</el-button>
          <el-button type="success" plain @click="router.back()">返 回</el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>


</template>

<script lang="ts" setup>
import router from "@/router";
import {computed, onMounted, reactive, ref} from "vue";
import type {FormInstance} from "element-plus";
import type {optionType} from "@/interface/activity";
import {tips, tipType} from "@/utils/tip";
import {get, post, put} from "@/api";
import {dicType, modeType} from "@/interface/enum";

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
  state: '',
  source: '',
  description: '',
  nextContactTime: '',
});

let id = router.currentRoute.value.params.id;
const mode = computed(()=>{
  if(id === undefined){
    return modeType.add
  }else {
    return modeType.update
  }
})
const  loadClue =async ()=>{
  id = router.currentRoute.value.params.id;
  await get("/api/clue/"+id).then(res=>{
    if(res.data.code === 200){
      clueForm.value = res.data.data;
    }
  }).catch(err=>{

  })
}
const title = computed(()=>{
  if(mode.value===modeType.update){
    return '修改线索'
  }else {
    return '添加线索'
  }

})

const checkPhoneExist = (rule: any, value:any, callback:any) =>{

     get("/api/clue/checkPhoneExist/"+value).then(res=>{
         if(res.data.code === 200){
           if(mode.value === modeType.add)
            return  callback(new Error('手机号已存在'));
           else
             return  callback();
         }else{
            return  callback();
         }
     }).catch(err=>{

     })
}
const clueFormRules = reactive({
  phone: [
    { required: true, message: '请输入正确的手机号', trigger: 'blur' },
    { min: 11, max: 11, message: '请输入11位正确的手机号', trigger: 'blur' },
    { validator: checkPhoneExist, trigger: 'blur'}
  ],
  fullName:[
    { min: 2, max: 10, message: '请输入姓名长度', trigger: 'blur' },
    { pattern: /^[a-zA-Z\u4e00-\u9fa5]+$/, message: '请输入正确的姓名', trigger: 'blur'}
  ],
  weixin:[
    { min: 4, max: 20, message: '请输入4-20位微信号', trigger: 'blur' },
    { pattern: /^[1-9a-zA-Z]{4,20}$/, message: '请输入正确的微信号', trigger: 'blur'}
  ],
  qq:[
    { min: 4, max: 12, message: '请确认您的QQ号长度', trigger: 'blur' },
    { pattern: /^[1-9]\d{4,12}$/, message: '请输入正确的QQ号', trigger: 'blur'},
  ],
  email:[
    { min: 4, max: 20, message: '请输入4-20位邮箱', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '请输入正确的邮箱', trigger: 'blur'}
  ],
  age:[
    { pattern: /^[1-9]\d{0,2}$/, message: '请输入正确的年龄', trigger: 'blur'}
  ],
  job:[
    { min: 2, max: 10, message: '请输入正确的职位', trigger: 'blur' },
    { pattern: /^[a-zA-Z\u4e00-\u9fa5]+$/, message: '请输入正确的职位', trigger: 'blur'}
  ],
  yearIncome:[
    { pattern: /^[1-9]\d{0,2}$/, message: '请输入正确的收入', trigger: 'blur'}
  ],
  address:[
    { min: 2, max: 20, message: '请输入地址长度', trigger: 'blur' },
    { pattern: /^[a-zA-Z\u4e00-\u9fa5]+$/, message: '请输入正确的地址', trigger: 'blur'}
  ],
  nextContactTime:[
    { required: true, message: '请选择下次联系时间', trigger: 'blur' },
  ],
  description:[
    { min: 2, max: 255, message: '请输入线索描述长度', trigger: 'blur' },
  ]
})



const addClueSubmit =  (formRef:FormInstance|undefined) => {
  if(!formRef) return;

   formRef.validate( (valid,errorFilds) => {
    if (valid) {
      if(mode.value === modeType.update){
        updateClue();
      }else{
        addClue();
      }
    }
  })

}
const addClue = () => {
  post("/api/clue",clueForm.value).then(res=>{
    if(res.data.code === 200){
      tips('添加成功',tipType.success)
      router.push('/dashboard/clue')
    }
  }).catch(err=>{
    tips('添加失败',tipType.error)
  })
}
const updateClue = () => {
  put("/api/clue",clueForm.value).then(res=>{
    if(res.data.code === 200){
      tips('修改成功',tipType.success)
      router.push('/dashboard/clue')
    }
  }).catch(err=>{
    tips('修改失败',tipType.error)
  })
}



interface option {
  id: number;
  typeValue: string;
}
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
  await loadClue();
})

</script>

<style  scoped>

</style>