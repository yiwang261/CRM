package com.wang.crm.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public enum DicTypeEnum {
    //1
    SEX("SEX", "性别"),
    //2
    APPELLATION("appellation", "称呼"),

    //3
    CLUE_STATE("clueState", "线索状态"),
    //4
    RETURN_PRIORITY("returnPriority", "回访优先级"),
    //5
    RETURN_STATE("returnState","回访状态"),
    //6
    SOURCE("source","来源"),
    //7
    STAGE("stage","阶段"),
    //8
    TRANSACTION_TYPE("transactionType","交易类型"),
    //9
    INTENTION_STATE("intentionState","意向状态"),
    //10
    NEED_LOAN("needLoan","是否需要贷款"),
    //11
    EDUCATIONAL("educational","学历"),
    //12
    USER_STATE("userState","用户状态"),
    //13
    NOTE_WAY("noteWay","跟踪方式"),
    //14
    PRODUCT("product","产品"),
    ACTIVITY("activity","活动")
   ;
    @Getter
    private String typeCode;

    private String typeName;


}
