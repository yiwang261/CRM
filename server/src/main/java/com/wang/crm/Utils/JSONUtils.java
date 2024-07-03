package com.wang.crm.Utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.support.spring6.http.codec.Fastjson2Decoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

    //jackson这个jar包转json


    /**
     * 把java对象转成json
     *
     * @param object
     * @return
     */
    public static String toJSON(Object object) {

            return JSON.toJSONString(object);
    }

    /**
     * 把json字符串转java对象
     *
     * @param json
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T toBean(String json, Class<T> clazz) {
            return JSON.parseObject(json,clazz);
    }
}
