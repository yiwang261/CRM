<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
import {ElLoading, type FormInstance, type FormRules} from "element-plus";
import {post} from "@/api";
import {tips, tipType} from "@/utils/tip";
import router from "@/router";
import removeToken, {TOKEN_KEY} from "@/utils/token";
import {Color} from "@/assets/color";

interface LoginVO {
  username: string;
  password: string;
  rememberMe: string;
}

const loginFormRef = ref<FormInstance>();
const loginForm = reactive<LoginVO>({
  username: '',
  password: '',
  rememberMe: '',
})
const loginRules = reactive<FormRules<LoginVO>>({
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    // {min: 10, max: 15, message: '长度在 10 到 15 个字符', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    // {min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur'}
  ]
})


// 开启加载动画
const loading = () => {
  return ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.6)',
  })
}

const submitForm = async (formEl: FormInstance | undefined) => {
  // console.log(loginFormRef.value)
  // loginFormRef.
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      // console.log('submit!')
      const formData = new FormData();
      formData.append("loginAct", loginForm.username)
      formData.append("loginPwd", loginForm.password)
      formData.append("rememberMe", loginForm.rememberMe.toString())
      post("/api/login", formData).then(res => {
        removeToken(); // 登录之前删除原有token

        let loadAnimation = loading();// 开启加载动画

        if (res.data.code === 200) {
          loadAnimation.close(); // 关闭加载动画
          if (loginForm.rememberMe) localStorage.setItem(TOKEN_KEY, res.data.data)
          else sessionStorage.setItem(TOKEN_KEY, res.data.data);
          success();
          router.push({
            path: '/dashboard'
          })

        } else {
          fail();
        }
      }).catch(err => {
        console.log(err)
      })
    } else {
      console.log('error submit!', fields)
    }
  })
}


//
// const resetForm = (formEl: FormInstance | undefined) => {
//   if (!formEl) return
//   formEl.resetFields()
// }


// const options = Array.from({ length: 10000 }).map((_, idx) => ({
//   value: `${idx + 1}`,
//   label: `${idx + 1}`,
// }))

const success = () => {
  tips("登录成功", tipType.success)
}
const fail = () => {
  tips("用户名或密码错误", tipType.error)
}


</script>

<template>

  <el-container>
    <el-aside width="auto">
      <img src="../assets/left.png" alt="error" style="max-height: 100vh; width: auto">
    </el-aside>
    <el-main>
      <h1 style="margin: 50px 20%">CRM管理系统</h1>
      <el-card class="login-card">
        <el-row justify="center" style="margin-bottom: 20px;">欢迎登录</el-row>
        <el-form :rules="loginRules" :model="loginForm" ref="loginFormRef" :hide-required-asterisk="true">
          <el-form-item prop="username" label="用户名" label-width="65px">
            <el-input placeholder="请输入用户名" v-model="loginForm.username" style="width: 260px"/>
          </el-form-item>
          <el-form-item prop="password" label="密码" label-width="65px">
            <el-input placeholder="密码" v-model="loginForm.password" type="password" style="width: 260px"/>
          </el-form-item>
          <el-form-item label-width="65px">
            <el-checkbox v-model="loginForm.rememberMe" label="记住密码"></el-checkbox>
          </el-form-item>
          <el-form-item style="display: grid;place-items: center">
            <el-button :color="Color.PRIMARY" @click="submitForm(loginFormRef)">登录</el-button>
          </el-form-item>
        </el-form>

      </el-card>

    </el-main>
  </el-container>
</template>

<style scoped>

.el-aside {
  height: 100vh;
  overflow: hidden;
}
.login-card{
  width: 400px ;
  height: 350px;
  margin-top: 100px;
}
.el-main {
  height: 100vh;
  width: 100%;
  padding: 0 350px 0 350px;
  overflow: hidden;
}



</style>