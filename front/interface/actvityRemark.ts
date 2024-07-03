import { get } from "@/api";

const url = "/api/activityRemark";

export interface ActivityRemark {
    id: string;
    activityId?: string;
    noteContent?: string;
    createTime?: string;
    createBy?: string;
    editTime?: string;
    editBy?: string;
}

export interface ActivityRemarkDTO{
  activityRemark : ActivityRemark,
  editByName : string,
  createByName : string
}

