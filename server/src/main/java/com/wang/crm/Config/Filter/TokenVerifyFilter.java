package com.wang.crm.Config.Filter;

import com.wang.crm.Constant.Constants;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Constant.CodeEnum;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.RedisService;
import com.wang.crm.Utils.JSONUtils;
import com.wang.crm.Utils.JWTUtils;
import com.wang.crm.Utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class TokenVerifyFilter extends OncePerRequestFilter {
    @Resource
    private RedisService<String,String> redisService;

    //springboot中自带的线程池
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals(Constants.LOGIN_URI)){
            filterChain.doFilter(request,response);
        }else {
            //从前端请求中获取token
            String token = request.getHeader(Constants.TOKEN_NAME);
            if (request.getRequestURI().equals(Constants.EXPORT_EXCEL_URI)) {
                token = request.getParameter("Authorization");
            }
            //如果token为空
            if(!StringUtils.hasText(token)){
                Result result = Result.error(CodeEnum.NO_LOGIN);

                String resultJSON = JSONUtils.toJSON(result);

                ResponseUtils.write(response,resultJSON);
                return;
            }

//            System.out.println(token);
            if(!JWTUtils.verifyJWT(token)){//如果token无效
                Result result = Result.error(CodeEnum.TOKEN_ERROR);
                ResponseUtils.write(response,JSONUtils.toJSON(result));
                return;
            }

            User user = JWTUtils.parseUserFromJWT(token);

            String redisToken = redisService.get(Constants.REDIS_JWT_KEY+user.getId());

            //如果redis中没有token
            if(!StringUtils.hasText(redisToken)){
                Result result = Result.error(CodeEnum.TOKEN_EXPIRED);
                ResponseUtils.write(response,JSONUtils.toJSON(result));
                return;
            }
            //如果redis中的token和请求中的token不相等
            if(!redisToken.equals(token)){
                Result result = Result.error(CodeEnum.TOKEN_ERROR);
                ResponseUtils.write(response,JSONUtils.toJSON(result));
                return;
            }
            //在SecurityContextHolder中设置认证信息
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user,user.getLoginPwd(),user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            //刷新token
//            new Thread(()->{
//
//                String rememberMe = request.getHeader("rememberMe");
//                if(Boolean.parseBoolean(rememberMe)){
//                    System.out.println(1);
//                    redisService.expire(Constants.REDIS_JWT_KEY + user.getId(), Constants.EXPIRED_TIME, TimeUnit.SECONDS);
//                }else {
//                    System.out.println(2);
//                    redisService.expire(Constants.REDIS_JWT_KEY + user.getId(), Constants.DEFAULT_TIME, TimeUnit.SECONDS);
//                }
//            }).start();


            //线程池执行
            taskExecutor.execute(()->{

                String rememberMe = request.getHeader("rememberMe");
                if(Boolean.parseBoolean(rememberMe)){
//                    System.out.println(1);
                    redisService.expire(Constants.REDIS_JWT_KEY + user.getId(), Constants.EXPIRED_TIME, TimeUnit.SECONDS);
                }else {
//                    System.out.println(2);
                    redisService.expire(Constants.REDIS_JWT_KEY + user.getId(), Constants.DEFAULT_TIME, TimeUnit.SECONDS);
                }
            });

            filterChain.doFilter(request,response);
        }
    }
}
