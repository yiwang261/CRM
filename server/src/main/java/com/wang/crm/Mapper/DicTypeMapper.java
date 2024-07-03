package com.wang.crm.Mapper;

import com.wang.crm.Pojo.DTO.DicTypeDTO;
import com.wang.crm.Pojo.Entity.DicType;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_dic_type(字典类型表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.DicType
*/
public interface DicTypeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DicType record);

    int insertSelective(DicType record);

    DicType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DicType record);

    int updateByPrimaryKey(DicType record);

    List<DicTypeDTO> getAllDicType();
}
