package com.wang.crm.Service.Impl;

import com.wang.crm.Mapper.ProductMapper;
import com.wang.crm.Pojo.Entity.Product;
import com.wang.crm.Service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Override
    public List<Product> getAllProduct() {
        return productMapper.getAllProduct();
    }
}
