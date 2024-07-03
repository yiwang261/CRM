package com.wang.crm.Mapper;

import com.wang.crm.Pojo.Entity.DicValue;

/**
* @author yiwan
* @description 针对表【t_dic_value(字典值表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.DicValue
*/
public interface DicValueMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DicValue record);

    int insertSelective(DicValue record);

    DicValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DicValue record);

    int updateByPrimaryKey(DicValue record);

}
