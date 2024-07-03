export interface State {
    
}
export interface Owner {
    
}
export interface Activity {
    
}
export interface NeedLoan {
        
}
export interface IntentionState {
    
}
export interface IntentionProduct {
    
}
export interface State {
    
}
export interface Source {
    
}

export interface ActivityClueVO{
    id?: number;
    ownerId?: number;
    activityId?: number;
    fullName?: string;
    appellation?: number;
    phone?: string;
    weixin?: string;
    qq?: string;
    email?: string;
    age?: number;
    job?: string;
    yearIncome?: number;
    address?: string;
    needLoan?: number;
    intentionState?: number;
    intentionProduct?: number;
    state?: number;
    source?: number;
    description?: string;
    nextContactTime?: string;
    createTime?: string;
    createBy?: number;
    editTime?: string;
    editBy?: number;
}


export interface ActivityClueQuery extends ActivityClueVO{
    currentPage?: number;
    size?: number;
}
