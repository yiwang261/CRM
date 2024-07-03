
export interface activity {
    id?:number|null,
    ownerId:number|null,
    name:string|null,
    startTime:string|null,
    endTime:string,
    cost:string,
    description:string,
    createTime?:string,
    createBy?:string,
    editTime?:string,
    editBy?:string,
}

export interface activityDTO {
    activityId : number,
    ownerName  : string,
    editByName ?: string,
    createByName?: string,
    remark?: string,
    activity   : activity
}

//分页查询用户列表
export interface activityQuery {
    ownerId: number|null,
    name: string,
    startTime: string,
    endTime: string,
    cost:string,
    createTime:string,
}