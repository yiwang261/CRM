export interface User {
    name : string,
    loginAct: string,
    phone : string,
    email : string,
    editTime : string,
    id : number|undefined,
    loginPwd : string,
    accountNoExpired : number|undefined,
    credentialsNoExpired : number|undefined,
    accountNoLocked : number|undefined,
    accountEnabled : number|undefined,
    editBy : number|undefined,
    createTime : string,
    createBy : number|undefined,
    lastLoginTime : string,
}
export interface UserVO{
    name : string,
    loginAct: string,
    phone : string,
    email : string,
    loginPwd : string,
    accountNoExpired : number|undefined,
    credentialsNoExpired : number|undefined,
    accountNoLocked : number|undefined,
    accountEnabled : number|undefined,
}

