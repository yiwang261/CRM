package com.wang.crm.Mapper;

import com.wang.crm.Com.DataScope;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.BaseQuery;
import com.wang.crm.Pojo.Query.UserQuery;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_user(用户表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.User
*/
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByLoginAct(String loginAct);

    User selectDetailByPrimaryKey(Integer id);
//create_by
    @DataScope(tableAlias = "t1",tableField = "id")
    List<User> selectUserByPage(UserQuery userQuery);

    int deleteUserInBatch(Integer[] ids);

    List<User> getOwnerList();
}
