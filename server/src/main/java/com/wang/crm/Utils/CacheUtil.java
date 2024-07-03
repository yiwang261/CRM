package com.wang.crm.Utils;


import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CacheUtil {



    public static <T> T getCacheData(Supplier<T> cacheData, Supplier<T> dbData, Consumer<T> cacheSave) {
        //从缓存中获取数据
        T data = cacheData.get();
        if(ObjectUtils.isEmpty(data)){
//            从数据库中获取
            data = dbData.get();
            if(!Objects.isNull(data)){
//                System.out.println("db:"+data);
                //保存数据到缓存
                cacheSave.accept(data);
            }
        }
        return data;
    }
}
