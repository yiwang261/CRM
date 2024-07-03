package com.wang.crm.Com;

import java.lang.annotation.*;


/**
 * @author yiwang
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface  DataScope {
    String tableAlias() default "";
    String tableField() default "";
}
