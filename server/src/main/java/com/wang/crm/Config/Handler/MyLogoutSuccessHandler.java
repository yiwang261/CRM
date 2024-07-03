package com.wang.crm.Config.Handler;

import com.wang.crm.Constant.Constants;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Constant.CodeEnum;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.RedisService;
import com.wang.crm.Utils.JSONUtils;
import com.wang.crm.Utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 退出成功处理器
 *
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private RedisService redisService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //退出成功，执行该方法，在该方法中返回json给前端，就行了
        User user = (User)authentication.getPrincipal();

        SecurityContextHolder.clearContext();

//        //删除一下redis中用户的jwt
        redisService.remove(Constants.REDIS_JWT_KEY + user.getId());

        //退出成功的统一结果
        Result result = Result.success(CodeEnum.LOGOUT);

        //把Result对象转成json
        String resultJSON = JSONUtils.toJSON(result);

        //把R以json返回给前端
        ResponseUtils.write(response, resultJSON);
    }
}
