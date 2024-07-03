package com.wang.crm.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum RoleType {
    Role_Admin(1,"admin"),//管理员
    Role_Salesman(0,"saler"),//销售员
    Role_SALER_MANAGER(2,"manager"),//销售经理
    Role_Marketing(3,"marketing ") ,//市场营销
    Role_Accountant(4,"accountant");//会计
    private final Integer code;
    private final String roleName;
}
