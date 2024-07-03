<script setup lang="ts">
import {reactive, ref, watch, watchEffect} from "vue";
import type {UserVO} from "@/interface/user";
import type {FormInstance, FormRules} from "element-plus";
import {post, put} from "@/api";
import {tips, tipType} from "@/utils/tip";
import {Color} from "@/assets/color";

const props = defineProps(["show",'userVO']);

// dialog控制
  const dialogFormVisible = ref<boolean>(false)
// watch(()=>props.show,(value, oldValue, onCleanup)=>{
//   dialogFormVisible.value = props.show;
// })
//如果是添加用户
  watchEffect(()=>{
    if(props.show){
      title.value = '添加用户';
      mode.value = modeType.add;
      dialogFormVisible.value = true;
    }else {
      dialogFormVisible.value = false;
    }
  })
  const emits = defineEmits(['add_visibleChange','RefreshDate']);
  const add_visibleChange = () =>{
    dialogFormVisible.value = false;
    emits('add_visibleChange')
  }

  enum modeType {
    update = 1,
    add = 2
  }
  const mode = ref<modeType>(modeType.add);
  // 如果是修改用户 会从UserManageView中传递过来的userVo获取数据
  watch(()=>props.userVO,()=>{
    userVO.value = props.userVO;
    userVO.value.loginPwd = '';
    title.value = '修改用户'
    mode.value = modeType.update;
  })

//表单验证
const userFormRef = ref<FormInstance>();

const userVO = ref<UserVO>({
  name : '',
  loginAct: '',
  phone : '',
  email : '',
  loginPwd : '',
  accountNoExpired : undefined,
  credentialsNoExpired : undefined,
  accountNoLocked : undefined,
  accountEnabled : undefined,
})
const userRules = reactive<FormRules<UserVO>>({
  name: [
    {required: true, message: '请输入姓名', trigger: 'blur'},
    {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
  ],
  loginAct: [
    {required: true, message: '请输入账号', trigger: 'blur'},
    {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
  ],
  phone: [
    {required: true, message: '请输入电话', trigger: 'blur'},
    { pattern: /^1[3|4|5|7|8][0-9]\d{8}$/, message: '请输入正确的手机号', trigger: 'blur'}
  ],
  email: [
    {required: true, message: '请输入邮箱地址', trigger: 'blur'},
    {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
  ],
  loginPwd: [
      {required: true, message: '请输入密码', trigger: 'blur'},
      {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'}
  ],
  accountNoExpired: [
    {required: true, message: '请选择是否过期', trigger: 'change'}
  ],
  credentialsNoExpired: [
    {required: true, message: '请选择是否过期', trigger: 'change'}
  ],
  accountNoLocked: [
    {required: true, message: '请选择是否锁定', trigger: 'change'}
  ],
  accountEnabled: [
    {required: true, message: '请选择是否启用', trigger: 'change'}
  ],

})

//添加用户
const subMit = async (userForm:FormInstance|undefined) =>{
   if(!userForm) return;
  await userForm.validate(async (valid, errorFields) => {
    if(valid){
      if(mode.value===modeType.add){
        console.log('add')
        console.log(userVO.value)
        await post('/api/user', userVO.value).then((res) => {
          if(res.data.code === 200){
            tips("添加成功",tipType.success);

          }else {
            tips(res.data.msg,tipType.error);
          }
        });
      }
      if(mode.value===modeType.update){
        console.log('update')

        await put('/api/user', userVO.value).then((res) => {
          if (res.data.code === 200) {
            tips("修改成功", tipType.success);
          } else {
            tips(res.data.msg, tipType.error);
          }
        })
      }
      emits('RefreshDate')
    }else {
      console.log('error submit!', errorFields)
      // tips("请填写正确的信息",tipType.warning);
    }
  })

  add_visibleChange()
}


const reset = (userFormRef:FormInstance|undefined) =>{
  if(!userFormRef) return;
  userFormRef.resetFields();
}

//动态改变标题
const title = ref<string>('添加用户');

const options = reactive([
  {
    label: '是',
    value: 1
  },
  {
    label: '否',
    value: 0
  }
])
</script>

<template>
  <el-dialog
      v-model="dialogFormVisible"
      :title="title" width="550"
      @close="()=>{$emit('add_visibleChange'); reset(userFormRef)} "
      top="10vh"
  >
    <el-form ref="userFormRef"
             :model="userVO" label-width="auto"
             style="max-width: 600px"
             label-suffix=":"
             :rules="userRules"
    >
      <el-form-item label="账号" prop="loginAct" required>
        <el-input v-model="userVO.loginAct" />
      </el-form-item>
      <el-form-item label="密码" prop="loginPwd" required>
        <el-input v-model="userVO.loginPwd" />
      </el-form-item>
      <el-row>
        <el-col :span="11">
          <el-form-item label="姓名" prop="name" required>
            <el-input v-model="userVO.name" />
          </el-form-item>
        </el-col>
        <el-col :span="11"  :offset="2">
          <el-form-item label="电话" prop="phone" required >
            <el-input v-model="userVO.phone"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="邮箱" prop="email" required>
        <el-input  v-model="userVO.email" />
      </el-form-item>
      <el-row>
        <el-col :span="11">
          <el-form-item label="账号未过期" prop="accountNoExpired" required>
            <el-select v-model="userVO.accountNoExpired" placeholder="请选择">
              <el-option v-for="(item,index) in options" :key="index" :label="item.label" :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11" :offset="2">
          <el-form-item label="密码未过期" prop="credentialsNoExpired" required>
            <el-select v-model="userVO.credentialsNoExpired" placeholder="请选择">
              <el-option v-for="(item,index) in options" :key="index" :label="item.label" :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="11">
          <el-form-item label="账号未锁定" prop="accountNoLocked" required>
            <el-select v-model="userVO.accountNoLocked" placeholder="请选择">
              <el-option v-for="(item,index) in options" :key="index" :label="item.label" :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11" :offset="2">
          <el-form-item label="账号可用" prop="accountEnabled" required>
            <el-select  v-model="userVO.accountEnabled" placeholder="请选择">
              <el-option v-for="(item,index) in options" :key="index" :label="item.label" :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

<!--      <el-form-item label="Activity form">-->
<!--        <el-input v-model="form.desc" type="textarea" />-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" @click="onSubmit">Create</el-button>-->
<!--        <el-button>Cancel</el-button>-->
<!--      </el-form-item>-->

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="info" @click="()=>{add_visibleChange(); reset(userFormRef)}">
          返回
        </el-button>
        <el-button :color="Color.PRIMARY" @click="()=>{ subMit(userFormRef);}">
          {{mode===modeType.add?'添加':'修改'}}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>