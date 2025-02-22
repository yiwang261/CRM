import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import {hasPermission} from "@/utils/hasPermissionDirective";

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}



app.use(createPinia())
app.use(router)

app.directive('hasPermission', {  mounted:hasPermission} )
app.use(ElementPlus, {
    locale: zhCn,
})

app.mount('#app')
