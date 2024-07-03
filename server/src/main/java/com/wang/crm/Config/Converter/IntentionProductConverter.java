package com.wang.crm.Config.Converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.wang.crm.Pojo.Entity.Product;
import com.wang.crm.Service.DicTypeService;
import com.wang.crm.Utils.GetBeanNonSpringManagedUtil;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class IntentionProductConverter implements Converter<Integer> {

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String cellAppellation = cellData.getStringValue();
        DicTypeService dicTypeService = GetBeanNonSpringManagedUtil.getBean(DicTypeService.class);
        List<Product> allProduct =dicTypeService.loadProductFromCache();

        if(allProduct != null){
            for (Product product : allProduct) {
                if (product.getName().equals(cellAppellation)) {
                    return product.getId();
                }
            }
        }


        return -1;


    }
}
