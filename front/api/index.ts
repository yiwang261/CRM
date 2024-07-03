import axios from "axios";
import {tips, tipType} from "@/utils/tip";
import {getToken} from "@/utils/token";


export const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000,
});

axiosInstance.interceptors.request.use(
    config => {

        let token: string | null = getToken();

        config.headers.Authorization = token;

        if(token)
        config.headers['rememberMe'] = true;

        return config;
    },
    error => {

        return Promise.reject(error);
    }
)


axiosInstance.interceptors.response.use(
    response => {
        // console.log(response);
        if (response.data.code !== 200) {
            // tips("系统错误",tipType.error);


            // if(router.currentRoute.value.path !== "/"){
            //     ElMessageConfirm(response.data.msg + ",是否跳转到登录页面？", tipType.warning)
            //         .then(() => { //确定
            //             removeToken();//删除token 并且跳转到登录页面
            //             router.push("/");
            //         })
            //         .catch(() => {//取消
            //             tips("取消", tipType.warning);
            //         })
            // }

            // return Promise.reject(new Error(response.data.msg || "Error"))
        }

        return response;
    },
    error => {
        return Promise.reject(error);
    }
)

export function get(url: string, params?: any) {
    return axiosInstance({
        url: url,
        method: "get",
        params: params
    })
}


export function post(url: string, data: any,header?:any) {
    return axiosInstance({
        url: url,
        method: "post",
        data: data,
        headers: header
    })
}

export function put(url: string, data: any) {
    return axiosInstance({
        url: url,
        method: "put",
        data: data
    })
}

export function del(url: string, data?: any) {
    return axiosInstance({
        url: url,
        method: "delete",
        data: data
    })
}

export default axiosInstance;