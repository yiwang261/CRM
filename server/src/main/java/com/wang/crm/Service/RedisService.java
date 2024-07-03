package com.wang.crm.Service;

import java.util.concurrent.TimeUnit;

public interface RedisService  <K,V>{
    void set(K key, V value);
    V get(K key);
    Boolean remove(K key);
    Boolean expire(K key, Long timeOut, TimeUnit unit);

    String getOnlyNumber(String redisOnlyNumberKey);
}
