import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router'
// import HomeView from "@/views/HomeView.vue";

const routes : RouteRecordRaw[]= [
  {
    path: '/',
    redirect: '/login',
  },
  {
    name: 'login',
    path:'/login',
    meta: {
      title: '登录',
    },
    component: () => import('@/views/LoginView.vue')
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    meta: {
      title: '首页',
    },
    component: () => import('@/views/DashboardView.vue'),
    children:[
      {
        path: '',
        name: 'home',
        meta: {
          title: '首页',
        },
        component: ()=>import('@/views/HomeView.vue')
      },
      {
        path: 'MarketActivity',
        name: 'MarketActivity',
        meta: {
          title: '市场活动',
          KeepAlive: true
        },
        component: () => import('@/views/MarketActivityView.vue')
      },
      {
        path: 'MarketActivity/:id',
        name: 'MarketActivityDetail',
        meta: {
          title: '市场活动详情',
        },
          props: true,
        component: () => import('@/views/MarketActivityDetailView.vue')
      },
      {
        path: 'clue',
        name: 'clue',
        meta: {
          title: '线索',
        },
        children:[
          {
            path: '',
            name: 'clueList',
            meta: {
                title: '线索管理',
            },
              component: () => import('@/components/Clue/ClueList.vue')
          },
          {
            path: 'add',
            name: 'clueAdd',
            meta: {
              title: '添加线索',
            },
              component: () => import('@/components/Clue/ClueAdd.vue')
          },
          {
            path: 'update/:id',
            name: 'clueUpdate',
            props: true,
            meta: {
                title: '修改线索',
            },
            component: () => import('@/components/Clue/ClueAdd.vue')
          },
            {
                path: 'detail/:id',
                name: 'clueDetail',
                props: true,
            meta: {
              title: '修改线索',
            },
                component: () => import('@/components/Clue/ClueDetail.vue')
          }
        ],
        component: () => import('@/views/ClueView.vue')
      },
      {
          path: 'customer',
          name: 'customer',
          meta: {
              title: '客户管理',
          },
          component: () => import('@/views/CustomerView.vue')
      },
      {
          path: 'customer/:id',
          name: 'customerDetail',
          props: true,
          meta: {
              title: '客户详情',
          },
          component: () => import('@/views/CustomerDetailView.vue')
      },
      {
      path: 'roleManage',
      name: 'roleManage',
      meta: {
        title: '角色管理',
      },
      component: () => import('@/views/RoleManageView.vue')
    },
      {
          path: 'userManage',
          name: 'userManage',
          meta: {
              title: '用户管理',
          },
          component: () => import('@/views/UserManageView.vue')
      },
      {
        //子路由不能以斜杆开头
        path : 'tran',
        component : () => import('@/views/TranView.vue'),
      },
      {
        //子路由不能以斜杆开头，这个叫动态路由，id是一个变量，值是动态的
        path : 'tran/detail/:id',
        props: true,
        //当访问 /dashboard/tran/detail/5 路由的时候，就渲染显示TranDetailView.vue页面
        component : () => import('@/views/TranDetailView.vue'),
      },

    ]
  },

]

const router = createRouter({
  // import.meta.env.BASE_URL
  history:  createWebHistory(),
  routes: routes
})



router.beforeEach( (to, from, next)=>{
  // 设置title
  document.title = to.meta.title as string;


  next();

})


router.afterEach( (to, from, next)=>{
    if (to.fullPath === '/dashboard') return;
    if (to.fullPath === '/') return;
    if (to.fullPath === '/login') return;
  sessionStorage.setItem("lastRoute", JSON.stringify(to.fullPath));
    // next();
})

export default router
