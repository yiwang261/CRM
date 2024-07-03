package com.wang.crm.Aspect;

import com.wang.crm.Com.DataScope;
import com.wang.crm.Constant.RoleType;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.BaseQuery;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author yiwang
 */
@Aspect
@Component
public class DataScopeAspect {

    @Pointcut(value = "@annotation(com.wang.crm.Com.DataScope)")
    private void pointcut(){}

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        DataScope declaredAnnotation = signature.getMethod().getDeclaredAnnotation(DataScope.class);
        String alias = declaredAnnotation.tableAlias();
        String field = declaredAnnotation.tableField();

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roleList = user.getRoleList();

        if(!roleList.contains(RoleType.Role_Admin.getRoleName())){
            Object arg1 = joinPoint.getArgs()[0];
            if(arg1 instanceof BaseQuery){
                BaseQuery baseQuery =  (BaseQuery) arg1;
                //               and t1.id=1
                // and t_remark.create_by = 2
                baseQuery.setFilterSql(" and "+alias + "." + field + "=" + user.getId());
            }
        }

        return joinPoint.proceed();

    }
}
