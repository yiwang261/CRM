import {get} from '@/api'
export const hasPermission = (el: Node, binding:any)=>{
    // console.log(binding.value)
    get("/api/login/info").then(res=>{
        if(res.data.code === 200){
            if(res.data.data.permissionList.indexOf(binding.value) === -1){
                // el.style.display = "none"
                if(el.parentNode)
                el.parentNode.removeChild(el)
            }
        }
    }).catch(err=>{
        console.log(err)
    })
}