package com.wang.crm.Pojo.Query;


import lombok.Data;

@Data
public class TranRemarkQuery extends BaseQuery {

    private Integer tranId;

    private Integer noteWay;

    private String noteContent;
}
