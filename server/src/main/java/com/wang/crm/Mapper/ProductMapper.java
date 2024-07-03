package com.wang.crm.Mapper;

import com.wang.crm.Pojo.Entity.Product;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_product(产品表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.Product
*/
public interface ProductMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> getAllProduct();
}
