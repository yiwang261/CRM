package com.wang.crm.Service;

import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.DTO.UserDTO;
import com.wang.crm.Pojo.Query.UserQuery;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService extends UserDetailsService {
    PageInfo<User> getUserByPage(UserQuery userQuery);

    Integer deleteUser(Integer id);

    User getUserById(Integer id);

    User getUserDetails(Integer id);

    Integer addUser(User user);

    int saveUser(UserDTO userVO);

    int updateUserByUserDTO(UserDTO userVO);

    int updateUser(User user);

    int deleteUserInBatch(Integer[] ids);

    List<User> getOwnerListByCache();
}
