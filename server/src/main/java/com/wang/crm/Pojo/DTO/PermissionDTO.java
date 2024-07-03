package com.wang.crm.Pojo.DTO;

import com.wang.crm.Pojo.Entity.Permission;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class PermissionDTO {
    private Integer id;
    private String name;
    private String icon;
    private String url;
    private String code;
    private List<PermissionDTO> children;


}
