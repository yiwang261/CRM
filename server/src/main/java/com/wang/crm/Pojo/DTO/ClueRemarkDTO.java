package com.wang.crm.Pojo.DTO;

import com.wang.crm.Pojo.Entity.DicValue;
import com.wang.crm.Pojo.Entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ClueRemarkDTO {
    private Integer id;
    private Integer clueId;
    private String noteContent;
    private Integer createBy;
    private String createByName;
    private Date createTime;
    private Integer editBy;
    private String editByName;
    private Date editTime;
    private Integer noteWay;
    private String noteWayName;
    private Integer deleted;
    private User createByDO;
    private User editByDO;
    private DicValue noteWayDO;
}
