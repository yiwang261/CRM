<script setup lang="ts">
import {RouterView} from 'vue-router'
import {onBeforeMount, onMounted,ref} from "vue";
import {getToken} from "@/utils/token";
import router from "@/router";
import {get} from "@/api";


onMounted(() => {
  freeLogin(); // 判断是否免登录

})

const freeLogin = () => {
  const token = getToken();
  if (token) {
    get("/api/login/checkToken", {})
        .then((res) => {
            if (res.data.code === 200) {
              router.push({
                name: 'dashboard'
              })
            } else {
              router.push({
                name: 'login'
              })
            }
        })
        .catch(err => {
            console.log(err)
        })
  }
}

</script>

<template>
  <div ref="div"></div>
  <RouterView/>
</template>

<style>
html, body {
  height: 100vh;
  width: 100vw;
}

* {
  margin: 0;
  padding: 0;
}





</style>
