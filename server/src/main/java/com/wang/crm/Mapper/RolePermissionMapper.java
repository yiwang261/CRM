package com.wang.crm.Mapper;

import com.wang.crm.Pojo.Entity.RolePermission;

/**
* @author yiwan
* @description 针对表【t_role_permission(角色权限关系表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.RolePermission
*/
public interface RolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

}
