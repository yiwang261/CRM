package com.wang.crm.Mapper;

import com.wang.crm.Pojo.Entity.Role;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_role(角色表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.Role
*/
public interface RoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectByUserId(Integer id);
}
