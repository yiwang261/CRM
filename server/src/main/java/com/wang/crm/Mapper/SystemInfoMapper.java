package com.wang.crm.Mapper;

import com.wang.crm.Pojo.Entity.SystemInfo;

/**
* @author yiwan
* @description 针对表【t_system_info(系统信息表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.SystemInfo
*/
public interface SystemInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SystemInfo record);

    int insertSelective(SystemInfo record);

    SystemInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemInfo record);

    int updateByPrimaryKey(SystemInfo record);

}
