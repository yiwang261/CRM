package com.wang.crm.Service.Impl;

import com.wang.crm.Constant.Constants;
import com.wang.crm.Constant.DicTypeEnum;
import com.wang.crm.Mapper.DicTypeMapper;
import com.wang.crm.Pojo.DTO.DicTypeDTO;
import com.wang.crm.Pojo.Entity.Activity;
import com.wang.crm.Pojo.Entity.DicValue;
import com.wang.crm.Pojo.Entity.Product;
import com.wang.crm.Service.ActivityService;
import com.wang.crm.Service.DicTypeService;
import com.wang.crm.Service.ProductService;
import com.wang.crm.Service.RedisService;
import com.wang.crm.Utils.CacheUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DicTypeServiceImpl implements DicTypeService {
    @Resource
    private DicTypeMapper dicTypeMapper;
    @Resource
    private RedisService<String, List<DicValue>> dicValueRedisService;

    @Resource
    private RedisService<String, List<Product>> productRedisService;
    @Resource
    private ProductService productService;


    @Resource
    private RedisService<String, List<Activity>> activityRedisService;

    @Resource
    private ActivityService activityService;
    @Override
    public List<DicValue> loadDicTypeValueFromCache(String typeCode) {
        if(typeCode==null) return null;

        List<DicValue> dicValueList = dicValueRedisService.get(typeCode);
        if(dicValueList!=null) return dicValueList;

        List<DicTypeDTO> allDicType = dicTypeMapper.getAllDicType();
        for (DicTypeDTO dicTypeDTO : allDicType) {
            String typeCodeFromDB = dicTypeDTO.getTypeCode();
            dicValueRedisService.set(Constants.REDIS_CACHED_KEY +typeCodeFromDB,dicTypeDTO.getDicValueList());
            activityRedisService.expire(Constants.REDIS_CACHED_KEY +typeCodeFromDB,1L, TimeUnit.HOURS);
            if(typeCode.equals(typeCodeFromDB)) return dicTypeDTO.getDicValueList();
        }
        return null;
    }

    @Override
    public List<Product> loadProductFromCache() {

       return CacheUtil.getCacheData(()->{
                    return productRedisService.get(Constants.REDIS_CACHED_KEY +DicTypeEnum.PRODUCT.getTypeCode());
                },
                ()->{
                    return productService.getAllProduct();
                },
                (productList)->{
                    productRedisService.set(Constants.REDIS_CACHED_KEY +DicTypeEnum.PRODUCT.getTypeCode(),productList);
                    activityRedisService.expire(Constants.REDIS_CACHED_KEY +DicTypeEnum.PRODUCT.getTypeCode(),1L, TimeUnit.HOURS);
                });
    }

    @Override
    public List<Activity> loadActivityFromCache() {
        return CacheUtil.getCacheData(()->{
                    return activityRedisService.get(Constants.REDIS_CACHED_KEY +DicTypeEnum.ACTIVITY.getTypeCode());
                },
                ()->{
                    return activityService.getOnGoingActivityList();
                },
                (activityList)->{
                    activityRedisService.set(Constants.REDIS_CACHED_KEY +DicTypeEnum.ACTIVITY.getTypeCode(),activityList);
                    activityRedisService.expire(Constants.REDIS_CACHED_KEY +DicTypeEnum.ACTIVITY.getTypeCode(),
                            1L, TimeUnit.HOURS);
                });
    }


}
