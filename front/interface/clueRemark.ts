export interface ClueRemark {
    id?: number,
    clueId?: number,
    noteWay?: number,
    noteContent: string,
    createTime?: string,
    createBy?: number,
    editTime?: string,
    editBy?: number,
    deleted?: number,
}

export interface clueRemarkQuery {
    clueId: number,
    current: number,
    size: number,
}