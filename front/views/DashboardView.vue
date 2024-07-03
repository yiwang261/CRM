<script setup lang="ts">
import CopRight from "@/components/CopRight.vue";
import Aside from "@/components/Aside.vue";

import {KeepAlive, onBeforeMount, onMounted, reactive} from "vue";
import {useCollapseStore} from "@/stores/collapse";
import {Expand, Fold} from "@element-plus/icons-vue";
import {ref} from "vue";
import {get} from "@/api";
import removeToken from "@/utils/token";
import router from "@/router";
import {ElMessageConfirm, tips, tipType} from "@/utils/tip";

const menuList = ref()
const lastRouterPath = ref<string|null>()


const logout = () => {

  ElMessageConfirm("确认退出？", tipType.warning).then(() => {
    get("/api/logout", {})
        .then(resp => {
          if (resp.data.code === 408) {
            router.push("/login")
            removeToken();
            // tips("退出成功", tipType.success)
          }
          else {
            ElMessageConfirm("退出失败,是否继续退出", tipType.error)
                .then(() => {
                  removeToken();
                  router.push("/login")
                }).catch(() => {
              tips("取消退出", tipType.error)
            })
          }
        })
        .catch(err => {
          // console.log(err)
        })
  }).catch(() => {})


}

const updateMy = () => {

}
const updatePwd = () => {

}

const username = ref<string>('');
const loadLoginUser = () => {
  get("/api/login/info", {})
    .then(resp => {
        username.value = resp.data.data.name;
        tips("欢迎回来, " + resp.data.data.name, tipType.success)

      menuList.value = resp.data.data.menuPermissionList;
        //如果children为空则删除children属性
      removeChildrenIfNoChildren(menuList.value);
    })
    .catch(err => {
    // console.log(err)
  })
}
const removeChildrenIfNoChildren = (list:any[]) => {
  if(!list || list.length === 0) return;
  list.forEach((item:any) => {
    if(item.children.length ===0 )delete item.children;
    else removeChildrenIfNoChildren(item.children);
  })
}


const recovery = () => {
  //记住上次路由地址 并恢复
  // let path = sessionStorage.getItem("lastRoute");
  // if(path){
  //   lastRouterPath.value = JSON.parse(path);
  //   if(lastRouterPath.value){
  //     router.push(lastRouterPath.value)
  //   }
  // }
}

onBeforeMount(() => {
  loadLoginUser();

})
onMounted(()=>{
  router.push("/dashboard")
  recovery();
  // lastRouterPath.value = router.currentRoute.value.path;
})



</script>

<template>

  <el-container style="width: 100vw;height: 100vh;overflow: hidden">
    <el-aside class="aside">
      <el-scrollbar>

        <el-menu
            :default-active="lastRouterPath"
            :collapse-transition="true"
            :collapse="useCollapseStore().collapse"
            active-text-color="#ffd04b"
            background-color="#334157"
            text-color="#fff"
            :unique-opened="true"
            :router="true"
        >
          <div style="width: auto;height: 30px;display: flex;justify-content: center;align-items: center;">
            <h3 style="line-height: 100%;">CRM</h3>
          </div>

          <Aside :menuList="menuList"/>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="head_left">
          <Component class="collapse_icon"
                     @click="useCollapseStore().change()"
                     :is="useCollapseStore().collapse?Expand:Fold"/>
        </div>

        <div style="float: right">
          <el-dropdown style="border: none">
              <span class="el-dropdown-link">
                {{ username }}
                <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
              </span>

            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="updateMy()">
                  我的资料
                </el-dropdown-item>
                <el-dropdown-item>
                  修改密码
                </el-dropdown-item>
                <el-dropdown-item divided @click="logout()">
                  退出
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main style="padding: 0;">
        <router-view v-slot="{ Component }">
          <keep-alive v-if="router.currentRoute.value.meta.KeepAlive">
            <component :is="Component" />
          </keep-alive>
          <component v-else :is="Component" />
        </router-view>
      </el-main>

      <el-footer class="footer">
        <CopRight style="display: grid;place-items: center;padding-top: 5px;"/>
      </el-footer>
    </el-container>

  </el-container>

</template>


<style scoped>
.head_left {
  float: left;
  height: 100%;
  display: flex;
  align-items: center;
}

.aside {
  background-color: #001529;
  width:auto;
}

.footer {
  background-color: aliceblue;
  height: 35px;
  line-height: 35px;
}

.header {
  background-color: ghostwhite;
  height: 64px;
}

.collapse_icon {
  height: 20px;
  width: 20px;
}

.collapse_icon:hover {
  cursor: pointer;
}

.el-dropdown-link {
  height: 64px;
  cursor: pointer;
  display: flex;
  align-items: center;
  margin-right: 10px;
}

.el-col {
 text-align: center;
}
</style>