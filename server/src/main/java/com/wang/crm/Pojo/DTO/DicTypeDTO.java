package com.wang.crm.Pojo.DTO;

import com.wang.crm.Pojo.Entity.DicValue;
import lombok.Data;

import java.util.List;

@Data
public class DicTypeDTO {
    /**
     * 主键，自动增长，字典类型ID
     */
    private Integer id;

    /**
     * 字典类型代码
     */
    private String typeCode;

    /**
     * 字典类型名称
     */
    private String typeName;

    /**
     * 备注
     */
    private String remark;

    private List<DicValue> dicValueList ;
}
