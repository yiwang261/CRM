<script setup lang="ts">
import {onBeforeMount, ref, watch} from 'vue'
import {ElTable} from 'element-plus'
import {del, get} from "@/api";
import type {User} from '@/interface/user';
import UserDetailDialog from "@/components/UserDetailDialog.vue";
import AddUserDialog from "@/components/UserDialog.vue";
import {ElMessageConfirm, tipType} from "@/utils/tip";
import { Color } from "@/assets/color";


const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const selectedUser = ref<User[]>([])

const handleSelectionChange = (item: User[]) => {
  selectedUser.value = item;
}


onBeforeMount(()=>{
  getUserList(1,10);
})

let userList = ref<User[]>()

let current = ref<number>(1);
let total = ref<number>(0);
// 监听current变化
watch(()=>current.value,(value, oldValue)=>{
    if(value !== oldValue){
      getUserList(value,10);
    }
})

//查看详情
const user_id = ref<number>(-1);
const show_user_detail = ref<boolean>(false);
const show_detail = (id:number) => {
  user_id.value = id;
  show_user_detail.value = true;
}

//添加用户
const show_add_user = ref<boolean>(false);
const addUser = () => {
  show_add_user.value = true;
}

 const deleteUser = async (id:number) => {
  await ElMessageConfirm("确认删除该用户吗？", tipType.warning);
  await del("/api/user/"+id,{}).then(response => {
    if(response.data.code === 200){
      getUserList(current.value,10);
    }
  })
}


const user = ref<User>();
const updateUser = async (id:number) => {
  show_add_user.value = true;
  await get("/api/user/"+id,{}).then(response => {
    if(response.data.code === 200){
      // console.log(response.data.data)
      user.value = response.data.data;
    }
  })
  await   getUserList(current.value,10);
}

//分页

const getUserList =async (current: number, size: number) => {
  await get("/api/user",{
    current: current,
    size: size
  }).then((response )=> {
    if(response.data.code === 200){

      userList.value = response.data.data.list;
      total.value = response.data.data.total;
    }
  })
}

const delBatch = async () => {
  await ElMessageConfirm("确认删除选中用户吗？", tipType.warning);
  await del("/api/user",
     selectedUser.value.map(item => item.id)  //和java中的流式编程一样 将一个值映射成一个新的值返回
  ).then(response => {
    if(response.data.code === 200){
      getUserList(current.value,10);
    }
  })
}

const refreshDate = async () => {
  await getUserList(current.value,10);
}
</script>

<template>

    <div style="margin:15px;">
      <el-button :color="Color.PRIMARY"  @click="addUser()"   v-has-permission="'user:add'" >添加用户</el-button>
      <el-button :color="Color.NEGATIVE" @click="delBatch()"  v-has-permission="'user:delete'"
                 :disabled="selectedUser.length<=0" >批量删除</el-button>
    </div>

    <el-table
        ref="multipleTableRef"
        :data="userList"
        style="width: 100%"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column type="index"  label="序号" width="55"/>
      <el-table-column property="loginAct" label="账号" width="120" />
      <el-table-column property="name" label="姓名" show-overflow-tooltip />
      <el-table-column property="phone" label="手机" show-overflow-tooltip />
      <el-table-column property="email" label="邮箱" show-overflow-tooltip />
      <el-table-column property="createTime" label="创建时间" show-overflow-tooltip />
      <el-table-column label="操作"   min-width="150"   show-overflow-tooltip>
        <template #default="scope">
          <el-button :color="Color.PRIMARY"   @click="show_detail(scope.row.id)" v-has-permission="'user:view'">详情</el-button>
          <el-button :color="Color.WARING" style="color: white"   @click="updateUser(scope.row.id)" v-has-permission="'user:edit'">修改</el-button>
          <el-button :color="Color.NEGATIVE"  @click="deleteUser(scope.row.id)" v-has-permission="'user:delete'">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top: 20px;margin-left: 20px;"
                   background
                   layout="prev, pager, next"
                   :total="total"
                   :page-size="10"
                   v-model:current-page="current"
    />

  <UserDetailDialog  :show="show_user_detail"    @detail_visibleChange="show_user_detail  = false"  :user_id="user_id"/>
  <UserDialog        :show="show_add_user" :userVO="user"      @add_visibleChange="show_add_user = false" @RefreshDate="refreshDate()"/>
</template>

<style scoped>

</style>