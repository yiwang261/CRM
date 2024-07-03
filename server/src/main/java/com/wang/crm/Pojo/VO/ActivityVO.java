package com.wang.crm.Pojo.VO;

import com.wang.crm.Pojo.Entity.Activity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityVO {
    private Integer activityId;
    private String ownerName;
    private Activity activity;
    private String createByName;
    private String editByName;
}
