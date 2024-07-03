import {defineStore} from "pinia";
import { ref } from "vue";
import {modeType} from "@/interface/enum";

export const useModeStore
    = defineStore('mode',()=>{
    const mode = ref<modeType>(modeType.add)
    const changeMode = ()=>{
        if(mode.value === modeType.add){
            mode.value = modeType.update
        }else if(mode.value === modeType.update){
            mode.value = modeType.add
        }
    }

    return { mode,changeMode}
})