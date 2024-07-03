package com.wang.crm.Service.Impl;

import com.wang.crm.Service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImp <K,V> implements RedisService<K,V> {
    @Resource
    private RedisTemplate<K,V> redisTemplate;
    @Resource
    private RedisTemplate<String,String> redisTemplate1;
    @Override
    public void set(K key, V value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public V get(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean remove(K key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Boolean expire(K key, Long timeOut, TimeUnit unit) {
        return redisTemplate.expire(key,timeOut,unit);
    }

    @Override
    public String getOnlyNumber(String redisOnlyNumberKey) {
            //希望体现出交易的时间          //java.time.* 包下的类，拿到当前的日期和时间（年月日时分秒）
            LocalDateTime localDateTime = LocalDateTime.now();
            //日期时间的格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String formattedDateTime = localDateTime.format(formatter);

            //从redis中拿到一个自增值，这个自增值每次都是不重复的，为什么redis自增的值不重复呢？原因是因为redis的命令的执行是单线程的；
            Long incrValue = redisTemplate1.opsForValue().increment(redisOnlyNumberKey);
            return formattedDateTime + incrValue;

    }
}
