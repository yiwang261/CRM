package com.wang.crm.Controller;

import com.wang.crm.Constant.DicTypeEnum;
import com.wang.crm.CrmApplication;
import com.wang.crm.Pojo.Entity.DicValue;
import com.wang.crm.Pojo.Entity.Product;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.DicTypeService;
import com.wang.crm.Service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DicController {

    @Resource
    private DicTypeService dicTypeService;
//    @PreAuthorize("hasAnyAuthority('dicvalue:list')")
    @GetMapping("/api/dic/{typeCode}")
    public Result getDicValue(@PathVariable(name = "typeCode") String typeCode){

        if(typeCode.equals(DicTypeEnum.PRODUCT.getTypeCode())){
            List<Product> products = dicTypeService.loadProductFromCache();
            return Result.success(products);
        }else if (typeCode.equals(DicTypeEnum.ACTIVITY.getTypeCode())){
            return Result.success(dicTypeService.loadActivityFromCache());
        }
        return Result.success(dicTypeService.loadDicTypeValueFromCache(typeCode));
    }


}
