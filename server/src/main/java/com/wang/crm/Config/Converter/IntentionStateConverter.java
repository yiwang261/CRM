package com.wang.crm.Config.Converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.wang.crm.Constant.DicTypeEnum;
import com.wang.crm.Pojo.Entity.DicValue;
import com.wang.crm.Service.DicTypeService;
import com.wang.crm.Utils.GetBeanNonSpringManagedUtil;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class IntentionStateConverter implements Converter<Integer> {

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String cellAppellation = cellData.getStringValue();
        DicTypeService dicTypeService = GetBeanNonSpringManagedUtil.getBean(DicTypeService.class);
        List<DicValue> dicValueList =dicTypeService.loadDicTypeValueFromCache(DicTypeEnum.APPELLATION.getTypeCode());


        if(dicValueList != null){
            for (DicValue dicValue : dicValueList) {
                if (dicValue.getTypeValue().equals(cellAppellation)) {
                    return dicValue.getId();
                }
            }
        }


        return -1;


    }
}
