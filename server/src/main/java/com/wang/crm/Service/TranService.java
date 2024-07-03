package com.wang.crm.Service;


import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.DTO.TranDTO;
import com.wang.crm.Pojo.Entity.Tran;
import com.wang.crm.Pojo.Query.TranQuery;

public interface TranService {

    int saveTran(TranQuery tranQuery);

    PageInfo<TranDTO> getTranByPage(Integer current);

    TranDTO getTranById(Integer tranId);
}
