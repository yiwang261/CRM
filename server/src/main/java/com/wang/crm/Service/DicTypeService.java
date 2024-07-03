package com.wang.crm.Service;

import com.wang.crm.Pojo.DTO.DicTypeDTO;
import com.wang.crm.Pojo.Entity.Activity;
import com.wang.crm.Pojo.Entity.DicValue;
import com.wang.crm.Pojo.Entity.Product;

import java.util.List;

public interface DicTypeService {

    List<DicValue> loadDicTypeValueFromCache(String typeCode);
    List<Product>  loadProductFromCache();
    List<Activity> loadActivityFromCache();

}
