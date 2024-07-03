package com.wang.crm.Mapper;

import com.wang.crm.Pojo.DTO.PermissionDTO;
import com.wang.crm.Pojo.Entity.Permission;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_permission(权限表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.Permission
*/
public interface PermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<PermissionDTO> selectMenuPermissionByRoleIds(List<Integer> roleIds);

    List<Permission> selectButtonPermissionByRoleIds(List<Integer> roleIds);
}
