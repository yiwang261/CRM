import {ElMessage, ElMessageBox} from "element-plus";

export enum tipType {
    success = "success",
    error = "error",
    warning = "warning",
    info = "info"
}
export function tips(msg: string,type : tipType) {
    ElMessage({
        message: msg,
        type: type,
        duration: 1500,
        plain: true,
    })
}

export function ElMessageConfirm(msg: string,type : tipType) {
    return ElMessageBox.confirm(
        msg,
        "提示",
        {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
        }
    )
}