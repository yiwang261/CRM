export enum modeType {
    add ='add',
    update = 'update'
}

export enum dicType {
    appellation = 'appellation',
    needLoan = 'needLoan',
    intentionState = 'intentionState',
    clueState = 'clueState',
    source = 'source',
    activity = 'activity',
    product = 'product',
    noteWay = 'noteWay',
    stage = 'stage',
}

export interface option {
    id: number;
    typeValue: string;
}
// 负责人下拉框
export interface optionType {
    name:string,
    id:string
}