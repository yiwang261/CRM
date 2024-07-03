<script lang="ts" setup>
import { del, get, post, put } from '@/api';
import {  type activityDTO } from '@/interface/activity';
import { type ActivityRemark, type ActivityRemarkDTO } from '@/interface/actvityRemark';
import router from '@/router';
import { ElMessageConfirm, tipType, tips } from '@/utils/tip';
import type { FormInstance, FormRules } from 'element-plus';
import { onMounted, reactive, ref, watch } from 'vue';
const props = defineProps(
    {id: String}
)
const current = ref<number>(1);
const total = ref<number>(0);


let id =  props.id;

const activityDetail = ref<activityDTO>({
    activityId : -1,
    ownerName  : '',
    editByName :'',
    createByName:'',
    remark:'',
    activity   : {
        id:-1,
        ownerId:-1,
        name:'',
        startTime:'',
        endTime:'',
        cost:'',
        description:'',
    }
});

const remarkSubmit =  (formRef:FormInstance|undefined) => {
    
    if(!formRef)return;

      formRef.validate(async(valid)=>{
        if(valid){
           await addActivityRemark();
           await getRemark();    
        }
    })    
}
const addActivityRemark = async () => {

    await post('/api/activity/remark/'+props.id,{
        remark:activityDetail.value.remark
    }).then(res => {
        if(res.data.code === 200){
            tips(res.data.msg,tipType.success);
        }
    }).catch(err => {
        tips(err,tipType.error);            
    })
}
const getActivityDetail = ()=> {
    get('/api/activity/'+props.id).then(res => {
        if(res.data.code === 200 && res.data.data!==null)        
        activityDetail.value = res.data.data;
        // console.log(res.data.data);
    }).catch(err => {
        console.log(err);
    })
}

const remarkDialogVisible = ref<boolean>(false);

const activityRemark = ref<ActivityRemark>({
    id: '',
    noteContent: '',
});
const showEditRemark = (remark : ActivityRemark) =>{
    remarkDialogVisible.value = true;

    activityRemark.value.id = remark.id;
    activityRemark.value.noteContent = remark.noteContent;
}

const submitEdit = async(formRef:FormInstance|undefined) =>{
    if(!formRef)return;
    await formRef.validate( (valid)=>{
        if(valid){
            ElMessageConfirm('是否修改活动备注',tipType.warning).then(() => {
                updateRemark()
            }).catch(err => {
              console.log(err);
            })
        }
    })

    await  getRemark();
}




const remarkData = ref<ActivityRemarkDTO[]>([{
    activityRemark: {
        id: '',
        activityId: '',
        noteContent: '',
        createTime: '',
        createBy: '',
        editTime: '',
        editBy: ''
    },
    editByName:'',
    createByName:'',
}]);

const deleteRemark =  (activityRemark : ActivityRemark) =>{
    ElMessageConfirm('是否删除活动备注',tipType.warning).then( async () => {
          await delRemark(activityRemark);
          await getRemark();
    }).catch(err => {
      console.log(err)
    })

}
const delRemark = async (activityRemark : ActivityRemark) => {
  await del("/api/activity/remark/"+activityRemark.id).then(res=>{
            if(res.data.code == 200){
              tips("删除成功",tipType.success)
            }
        }).catch(err=>{
            console.log(err)
            tips("取消删除",tipType.warning)
        })
}
const updateRemark = async () =>{
    await put("/api/activity/remark",activityRemark.value).then(res=>{
        if(res.data.code == 200){
            tips("修改备注成功",tipType.success);
            getRemark();
            remarkDialogVisible.value = false;
        }else{
            tips("修改备注失败",tipType.error);
        }
    }).catch((err)=>{
        console.log(err)
        tips("修改备注失败",tipType.error);
    })
}

const getRemark = async () =>{
    await get("/api/activity/remark/"+props.id,{
        current:current.value,
        size:4
    }).then(res=>{
        // console.log(res.data.data.total);
        remarkData.value = res.data.data.list;
        total.value      = res.data.data.total;
    }).catch(err=>{
        console.log(err)
    })
}

watch(()=>current.value,()=>{
    getRemark()
})

onMounted( () => {
    getActivityDetail();
    getRemark();
})


// 活动备注 表单验证
const activityDetailRef = ref();

const activityRemarkRef = ref();
const remarkRules = reactive<FormRules<activityDTO>>({
    remark: [
        { required: true, message: '请输入活动备注', trigger: 'blur' },
        { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
    ]
})

const noteContentRules = reactive<FormRules<ActivityRemark>>({
    noteContent: [
        { required: true, message: '请输入活动备注', trigger: 'blur' },
        { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
    ]
})



</script>

<template>
  <el-row justify="center" >
    <h1 style="margin:15px;">市场活动详情</h1>
  </el-row>
   <el-form ref="activityDetailRef" :model="activityDetail" :rules="remarkRules">
        <!-- <el-form-item label-width="90px" label="ID:" prop="name">
            <el-input v-model="activityDetail.activityId" disabled/>
        </el-form-item> -->
        <el-form-item label-width="90px" label="活动名称:" >
            <el-input v-model="activityDetail.activity.name" :readonly="true"/>
        </el-form-item>

        <el-form-item label-width="90px" label="负责人">
            <el-input v-model="activityDetail.ownerName" :readonly="true"></el-input>
        </el-form-item>

        <el-form-item label-width="90px" label="活动预算">
            <el-input v-model="activityDetail.activity.cost" :readonly="true"></el-input>
        </el-form-item>

        <el-form-item label-width="90px" label="开始时间">
            <el-input v-model="activityDetail.activity.startTime" :readonly="true"></el-input>
        </el-form-item>

        <el-form-item label-width="90px" label="结束时间">
            <el-input v-model="activityDetail.activity.endTime" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label-width="90px" label="活动描述">
            <el-input v-model="activityDetail.activity.description" :readonly="true"></el-input>
        </el-form-item>

        <el-form-item label-width="90px" label="创建时间">
            <el-input v-model="activityDetail.activity.createTime" :readonly="true"></el-input>
        </el-form-item>
        
        <el-form-item label-width="90px" label="创建人">
            <el-input v-model="activityDetail.createByName" :readonly="true"></el-input>
        </el-form-item>
        
        <el-form-item label-width="90px" label="编辑时间">
            <el-input v-model="activityDetail.activity.editTime" :readonly="true"></el-input>
        </el-form-item>

        <el-form-item label-width="90px" label="编辑时间">
            <el-input v-model="activityDetail.activity.editTime" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label-width="90px" label="编辑人">
            <el-input v-model="activityDetail.editByName" :readonly="true"></el-input>
        </el-form-item>

        <el-form-item label-width="90px" label="活动描述" prop="remark">
            <el-input v-model="activityDetail.remark"  placeholder="请填写活动描述" type="textarea" :rows="4" />
        </el-form-item>
   </el-form>


   <el-row>
        <el-col :span="3" :offset="2">    
            <el-button  @click="router.back()">返回</el-button>
        </el-col>
        <el-col :span="3">    
            <el-button type="primary" @click="remarkSubmit(activityDetailRef)">提交</el-button>
        </el-col>
   </el-row>

   <el-divider/>

    <el-table :data="remarkData" style="width: 100%">
        <el-table-column type="index"                                       width="50" />
        <el-table-column prop="activityRemark.noteContent"  label="备注内容" width="180" />
        <el-table-column prop="activityRemark.createTime"   label="备注时间" width="180" />
        <el-table-column prop="createByName"                label="备注人"   width="180" />
        <el-table-column prop="activityRemark.editTime"     label="编辑时间" />
        <el-table-column prop="editByName"                  label="编辑人" />

        <el-table-column label="操作">
            <template #default="scoped">
                <el-button type="warning" round @click="showEditRemark(scoped.row.activityRemark)">编辑</el-button>
                <el-button type="danger"  round @click="deleteRemark(scoped.row.activityRemark)" >删除</el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-pagination style="margin-top: 20px;margin-left: 20px;"
                   background
                   layout="prev, pager, next"
                   :total="total"
                   :page-size="4"
                   v-model:current-page="current"
    />

    <el-dialog title="查看备注详情" v-model="remarkDialogVisible" width="50%" style="height: 45%;" :draggable="true">
        <el-form ref="activityRemarkRef" :model="activityRemark" :rules="noteContentRules">
            <el-form-item   prop="noteContent">
                <el-input
                    v-model="activityRemark.noteContent"
                    :rows="10"
                    type="textarea"
                    placeholder="请输入备注详情"
                />  
            </el-form-item>
        </el-form>

        <template #footer>
            <el-button @click="remarkDialogVisible = false">取消</el-button>
            <el-button type="primary"  @click="submitEdit(activityRemarkRef)">确定</el-button>
        </template>
    </el-dialog>
</template>

<style  scoped>

</style>