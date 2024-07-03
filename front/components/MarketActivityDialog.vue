<script setup lang="ts">
import {get, post, put} from '@/api'
import type {activity, optionType} from "@/interface/activity";
import {computed, onMounted, reactive, ref, watch} from 'vue'
import {type FormInstance, type FormRules} from "element-plus";
import {ElMessageConfirm, tips, tipType} from "@/utils/tip";
import {modeType} from "@/interface/enum";
import {Color} from "@/assets/color";

const props = defineProps({
  visible: {
    type: Boolean,
    default: false,
  },
  mode: {
    type: String,
    default: modeType.add,
  },
  acactivityId:{
    type: Number,
    default: null,
  },
});

//控制显示
const emit = defineEmits(["visible-change","refresh-data"]);
const visible = ref<boolean>(false);
watch(()=>props.visible,()=>{
  visible.value = props.visible;
  if(props.visible===true &&props.mode === modeType.update){
    getActivityById();
  }
})

const getActivityById = () => {
  get("/api/activity/"+props.acactivityId,{}).then(res=>{      
      activityForm.value = res.data.data.activity;
    }).catch(err=>{
      tips(err.message,tipType.error)
    })
}

//模式
const titleComputed =  computed(() => {
  switch (props.mode) {
    case modeType.add:
      return "添加市场活动";
    case modeType.update:
      return "修改市场活动";
  }
})
const btnComputed =  computed(() => {
  switch (props.mode) {
    case modeType.add:
      return "添加";
    case modeType.update:
      return "修改";
  }
})

const activityFormRef = ref<FormInstance>();


const activityForm = ref<activity>({
  ownerId:null,
  name:'',
  startTime:'',
  endTime:'',
  cost:'',
  description:'',
});
const activitFormRules = reactive<FormRules<activity>>({
  ownerId: [
    { required: true, message: '请选择负责人', trigger: 'change' },
  ],
  name: [
    { required: true, message: '请输入活动名称', trigger: 'blur' },
  ],
  startTime: [
    { required: true, message: '请输入开始时间', trigger: 'blur' },
  ],
  endTime: [
    { required: true, message: '请输入结束时间', trigger: 'blur' },
  ],
  cost: [
    { required: true, message: '请输入预算', trigger: 'blur' },
    { pattern: /^(0|[1-9]\d*)(\.\d{1,2})?$/, message: '预算必须是整数或两位小数', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入描述', trigger: 'blur' },
    { max: 250, message: '描述不能超过250个字符', trigger: 'blur' }
  ],
})
const onAddActivity = (formRef:FormInstance|undefined) => {
  if(!formRef) return;
  formRef.validate((valid,errorFields) => {
    if(valid){
      if(props.mode === modeType.add){
        post("/api/activity",activityForm.value).then(res=>{
          handleClose();
          emit("refresh-data");
          tips("添加成功",tipType.success);
        }).catch(error=>{
          tips(error.message,tipType.error)
        })
      }
      if(props.mode === modeType.update){
        console.log(activityForm.value)
        put("/api/activity/"+props.acactivityId,activityForm.value).then(res=>{
          console.log(res.data);
          handleClose();
          emit("refresh-data");
          tips("修改成功",tipType.success);
        }).catch(error=>{
          tips(error.message,tipType.error)
        })
      }
   
    }

  })

}


const ownerOptions = ref<[optionType]>();
const getOwner = () => {
  get("/api/owner",{}).then(res=>{
    ownerOptions.value = res.data.data;
  }).catch(err=>{
    tips(err.message,tipType.error)
  })
}
//重置表单
const resetFields = (formRef:FormInstance|undefined) => {
  if(!formRef) return;
  formRef.resetFields();
}
const handleClose = () => {
  resetFields(activityFormRef.value);
  emit("visible-change");
}

const handleBeforeClose = () => {
  ElMessageConfirm("确定关闭？",tipType.warning).then(()=>{
     handleClose();
  }).catch(error=>{
    // console.log(error);
  })
}
const handleCancel = () => {
  handleBeforeClose();
}
</script>

<template>
  <el-dialog v-model="visible" :title="titleComputed"
            :center="true"
             width="45vw" top="15vh"
             style="height: 60vh;"
             :before-close="handleBeforeClose"
             @close="handleClose()">
    <el-form ref="activityFormRef" :model="activityForm" :rules="activitFormRules">
      <el-row>
        <el-col :span="10">
          <el-form-item label-width="90px" label="活动名称:" prop="name">
            <el-input v-model="activityForm.name" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="10">
          <el-form-item label-width="90px" label="负责人:"  prop="ownerId">
            <el-select v-model="activityForm.ownerId"
                       clearable placeholder="请选择负责人"
                       @click="getOwner"
            >
              <el-option v-for="owner in ownerOptions"
                         :key="owner.id"
                         :label="owner.name"
                         :value="owner.id"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10" :offset="1">
          <el-form-item label-width="90px" label="活动预算:" prop="cost">
            <el-input v-model="activityForm.cost" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item  label-width="90px" label="开始时间:" prop="startTime">
            <el-date-picker
                v-model="activityForm.startTime"
                type="datetime"
                placeholder="前选择创建时间"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
                date-format="YYYY/MM/DD ddd"
                time-format="A hh:mm:ss"
                style="width:250px; "
            />
          </el-form-item>

        </el-col>

        <el-col :span="10" :offset="1">
          <el-form-item label-width="90px" label="结束时间:" prop="endTime">
            <el-date-picker
                v-model="activityForm.endTime"
                type="datetime"
                placeholder="前选择创建时间"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
                date-format="YYYY/MM/DD ddd"
                time-format="A hh:mm:ss"
                style="width:250px; "
            />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row>
        <el-col :span="21">
          <el-form-item label-width="90px" label="活动描述:" prop="description">
            <el-input v-model="activityForm.description" place-holder="aaaa" type="textarea" :rows="4" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
        <el-row justify="center">
              <el-col :span="2">
                <el-button :color="Color.PRIMARY" @click="onAddActivity(activityFormRef)">{{btnComputed}}</el-button>
              </el-col>

              <el-col :span="2" :offset="2">
                <el-button type="info" @click="handleCancel()">取消</el-button>
              </el-col>
        </el-row>
    </template>
  </el-dialog>

</template>

<style scoped>
.el-row{
  margin-bottom: 10px;
}
</style>