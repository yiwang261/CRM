package com.wang.crm.Pojo.DTO;

import com.alibaba.fastjson2.annotation.JSONField;
import com.wang.crm.Pojo.Entity.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class CustomerDTO {
    Integer id;
    private String name;
    private String phone;
    private String weChat;
    private Integer age;
    private String email;
    private String address;
    private ClueDTO clueDTO;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date nextContactTime;
}
