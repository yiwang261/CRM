<script setup lang="ts">
import {ref, watch,watchEffect} from "vue";
import type { User } from "@/interface/user"
import {get} from "@/api";
import {Color} from "@/assets/color";


const user = ref({
  name : '',
  loginAct: '',
  phone : '',
  email : '',
  editTime : '',
  id : -1,
  loginPwd : '',
  accountNoExpired : -1,
  credentialsNoExpired : -1,
  accountNoLocked : -1,
  accountEnabled : -1,
  editBy : -1,
  createTime : '',
  createBy : -1,
  lastLoginTime : '',
  editName : '',
  createName :''
})

  const props = defineProps(["show","user_id"]);


  const dialogFormVisible = ref<boolean>(false)


  watch(() => props.show, (newVal) => {
    if(newVal){
      dialogFormVisible.value = true;
      getUserDetail(props.user_id);
    }else{
      dialogFormVisible.value = false;
    }
  })
  // watchEffect(  ()=>{
  //    if(props.show){
  //       dialogFormVisible.value = true;
  //       getUserDetail(props.user_id);
  //     }else{
  //       dialogFormVisible.value = false;
  //     }
  // })

const getUserDetail = (id:number) => {
  get("/api/user/"+id,{}).then(response => {
    if(response.data.code === 200){
      // console.log(response.data.data)
      user.value = response.data.data;
    }
  })
}


</script>

<template>

  <el-dialog
      v-model="dialogFormVisible"
      title="用户详情" width="500"
      @close="$emit('detail_visibleChange')"
      top="10vh"
  >
    <el-form label-suffix=":" size="small" disabled>
      <el-form-item label="用户ID" >
        {{user.id}}
      </el-form-item>
      <el-form-item label="用户名">
        {{user.name}}
      </el-form-item>
      <el-form-item label="登录账号">
        {{user.loginAct}}
      </el-form-item>
      <el-form-item label="手机号">
        {{user.phone}}
      </el-form-item>
      <el-form-item label="邮箱">
        {{user.email}}
      </el-form-item>
      <el-form-item label="创建时间">
        {{user.createTime}}
      </el-form-item>
      <el-form-item label="最后登录时间">
        {{user.lastLoginTime}}
      </el-form-item>
      <el-form-item label="修改时间">
        {{user.editTime}}
      </el-form-item>
      <el-form-item label="创建人">
        {{user.createName}}
      </el-form-item>

      <el-form-item label="编辑人">
        {{user.editName}}
      </el-form-item>
      <el-form-item label="账号是否过期">
        {{user.accountNoExpired==0?"是":"否"}}
      </el-form-item>
      <el-form-item label="账号是否锁定">
        {{user.accountNoLocked==0?"是":"否"}}
      </el-form-item>
      <el-form-item label="账号是否可用">
        {{user.accountEnabled==1?"是":"否"}}
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button :color="Color.PRIMARY" @click="()=>{dialogFormVisible = false;$emit('detail_visibleChange')}">
          返回
        </el-button>
      </div>
    </template>
  </el-dialog>



</template>

<style scoped>

</style>