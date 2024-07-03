import {defineStore} from "pinia";
import { ref } from "vue";
export const useCollapseStore= defineStore('collapse',()=>{
    const collapse = ref<boolean>(false)
    // const changeCollapse = ()=>{
    //     collapse.value = !collapse.value
    // }
    const change = ()=>{
        collapse.value = !collapse.value
    }
    return {collapse,change}
})