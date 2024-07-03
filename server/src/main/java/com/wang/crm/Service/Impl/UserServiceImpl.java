package com.wang.crm.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.crm.Constant.Constants;
import com.wang.crm.Mapper.PermissionMapper;
import com.wang.crm.Mapper.RoleMapper;
import com.wang.crm.Mapper.UserMapper;
import com.wang.crm.Pojo.DTO.PermissionDTO;
import com.wang.crm.Pojo.Entity.Permission;
import com.wang.crm.Pojo.Entity.Role;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.DTO.UserDTO;
import com.wang.crm.Pojo.Query.UserQuery;
import com.wang.crm.Service.RedisService;
import com.wang.crm.Service.UserService;
import com.wang.crm.Utils.CacheUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;


    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RedisService<String,List<User>> redisService;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByLoginAct(username);
        if(user == null){
            throw new UsernameNotFoundException("账号不存在");
        }
        // 获取角色
        List<Role> roleList = roleMapper.selectByUserId(user.getId());
        List<String> collect = roleList.stream().map(role -> role.getRole()).collect(Collectors.toList());

        // 获取角色id
        List<Integer> roleIds = roleList.stream().map(role -> role.getId()).toList();

        // 获取菜单权限
        List<PermissionDTO> menuPermissionList =  permissionMapper.selectMenuPermissionByRoleIds(roleIds);
        user.setMenuPermissionList(menuPermissionList);

        // 获取权限
        List<Permission> buttonPermissionList =  permissionMapper.selectButtonPermissionByRoleIds(roleIds);
        List<String> permissionList = buttonPermissionList.stream()
                .map(permission -> permission.getCode())
                .toList();
        user.setPermissionList(permissionList);

        user.setRoleList(collect);
        return user;
    }

    @Override
    public PageInfo<User> getUserByPage(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getCurrent(),userQuery.getSize());

        User u = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userQuery.setId(u.getId());


        List<User> list = userMapper.selectUserByPage(userQuery);
        PageInfo<User> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public Integer deleteUser(Integer id) {

        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User getUserById(Integer id) {
//        Long lID = Integer.toUnsignedLong(id);

        return userMapper.selectByPrimaryKey(id);
    }

    public User getUserDetails(Integer id){
        return userMapper.selectDetailByPrimaryKey(id);
    }

    @Override
    public Integer addUser(User user) {
       return userMapper.insert(user);
    }

    @Override
    public int saveUser(UserDTO userVO) {
        User user = new User();
        if(userVO != null){
            BeanUtils.copyProperties(userVO,user);
        }

        user.setLoginPwd(passwordEncoder.encode(userVO.getLoginPwd()));


        //获取当前用户id
        User user1 = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setCreateTime(new Date());
        user.setCreateBy(user1.getId());
        user.setCreateBy(user1.getId());
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateUserByUserDTO(UserDTO userDTO) {
        if(userDTO != null){
            if(!userDTO.getLoginPwd().isEmpty()){
                userDTO.setLoginPwd(passwordEncoder.encode(userDTO.getLoginPwd()));
            }
            User user = new User();

            BeanUtils.copyProperties(userDTO,user);


            user.setEditTime(new Date());
            User u = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setEditBy(u.getId());
            user.setEditTime(new Date());
            return userMapper.updateByPrimaryKeySelective(user);
        }
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteUserInBatch(Integer[] ids) {
        return userMapper.deleteUserInBatch(ids);
    }

    @Override
    public List<User> getOwnerListByCache() {
        return CacheUtil.getCacheData(
                () ->  redisService.get(Constants.REDIS_OWNER_KEY),
                () -> userMapper.getOwnerList(),
                (data) -> redisService.set(Constants.REDIS_OWNER_KEY, data)
        );

    }

}
