package com.wang.crm.Controller;


import com.github.pagehelper.PageInfo;
import com.wang.crm.Constant.Constants;
import com.wang.crm.Pojo.DTO.UserDTO;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.UserQuery;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {



    @Resource
    private UserService userService;

    @GetMapping("/login/info")
    public Result<User> loginInfo(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        user.setLoginPwd(null);
        return Result.success(user);
    }

    @GetMapping("/login/checkToken")
    public Result freeLogin(){
        return Result.success();
    }

    /**
     * @Author: yiwang
     * @Description: 获取用户信息 分页
     * @return
     */
    @PreAuthorize("hasAnyAuthority('user:list')")
    @GetMapping("/user")
    public Result getUserByPage(UserQuery userQuery){
        PageInfo<User> userByPage = userService.getUserByPage(userQuery);
//        System.out.println(userByPage);
        return Result.success(userByPage);
    }
    @PreAuthorize("hasAnyAuthority('user:add')")
    @PostMapping("/user")
    public Result addUser(@RequestBody UserDTO userVO){
        System.out.println(userVO);
        return  userService.saveUser(userVO)==1 ? Result.success() : Result.error();
    }
    @PreAuthorize("hasAnyAuthority('user:view')")
    @GetMapping("/user/{id}")
    public Result getUserById(@PathVariable("id") Integer id){
        User userById = userService.getUserDetails(id);
        return userById==null?Result.error("该用户不存在"):Result.success(userById);
    }
    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("/user/{id}")
    public Result deleteUser(@PathVariable("id") Integer id){
        return userService.deleteUser(id)==1?Result.success():Result.error();
    }
    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("/user")
    public Result delUserBatch(@RequestBody Integer[] ids){
        return ids.length==userService.deleteUserInBatch(ids)
                ?Result.success():Result.error("删除失败");
    }


    @PreAuthorize("hasAnyAuthority('user:edit')")
    @PutMapping("/user")
    public Result updateUser(@RequestBody UserDTO userVO){
//        System.out.println(userVO);
        return userService.updateUserByUserDTO(userVO)==1?Result.success():Result.error();
    }

    @GetMapping("/owner")
    public Result<List<User>> getOwnerList(){
        List<User> ownerList = userService.getOwnerListByCache();
        return Result.success(ownerList);
    }
}
