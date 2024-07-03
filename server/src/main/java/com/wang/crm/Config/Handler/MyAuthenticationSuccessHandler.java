package com.wang.crm.Config.Handler;

import com.wang.crm.Constant.Constants;
import com.wang.crm.Mapper.UserMapper;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.RedisService;
import com.wang.crm.Utils.JSONUtils;
import com.wang.crm.Utils.JWTUtils;
import com.wang.crm.Utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisService<String,String> redisService;

    @Resource
    private UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //登录成功，执行该方法，在该方法中返回json给前端，就行了
        User user = (User) authentication.getPrincipal();

        //更新登录时间
        user.setLastLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);


        //1、生成jwt
        //把tUser对象转成json作为负载数据放入jwt
        String userJSON = JSONUtils.toJSON(user);
        String jwt = JWTUtils.createJWT(userJSON);


        //写入redis
        redisService.set(Constants.REDIS_JWT_KEY + user.getId(), jwt);

//        设置jwt
        String rememberMe = request.getParameter("rememberMe");
//        System.out.println(rememberMe);
        if(Boolean.parseBoolean(rememberMe)){
            redisService.expire(Constants.REDIS_JWT_KEY + user.getId(), Constants.EXPIRED_TIME, TimeUnit.SECONDS);
        }else {
            redisService.expire(Constants.REDIS_JWT_KEY + user.getId(), Constants.DEFAULT_TIME, TimeUnit.SECONDS);
        }
        //登录成功的统一结果
        Result<String> result = Result.success(jwt,"登陆成功");

        //把R对象转成json
        String resultJSON = JSONUtils.toJSON(result);

        //把R以json返回给前端
        ResponseUtils.write(response, resultJSON);
    }
}
