package com.wang.crm.Pojo.Query;


import lombok.Data;

@Data
public class CustomerRemarkQuery extends BaseQuery {

    private Integer customerId;

    private Integer noteWay;

    private String noteContent;
}
