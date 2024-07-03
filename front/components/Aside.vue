<script setup lang="ts">
  import {useRouter} from "vue-router";
  import router from "@/router";
  import {useCollapseStore} from "@/stores/collapse";

  const list = defineProps({
    menuList: {
      type: Array<any>,
    }
  })
  // const pushRouter = (path : string) => {
  //   router.push(path)
  // }
  //element-ui 的el-menu组件自带router  只需要将index属性设置为path即可
//   就不需要自己写路由跳转了
</script>

<template>
    <template v-for="item in list.menuList">
      <template v-if="item.children">
        <el-sub-menu :index="item.url" style="overflow: hidden">
          <template #title>
            <el-icon><component :is="item.icon"/></el-icon>
            <span>{{item.name}}</span>
          </template>

          <Aside :menuList="item.children" />
        </el-sub-menu>
      </template>

      <template v-else>
        <el-menu-item :index=item.url >
          <el-icon><component :is="item.icon"/></el-icon>
          <span>{{ item.name }}</span>
        </el-menu-item>
      </template>

    </template>


</template>

<style lang="css" scoped>

</style>