package com.wang.crm.Service;


import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.Entity.TranRemark;
import com.wang.crm.Pojo.Query.TranRemarkQuery;

public interface TranRemarkService {

    int saveTranRemark(TranRemarkQuery tranRemarkQuery);

    PageInfo<TranRemark> getTranRemarkByPage(Integer current, Integer tranId);
}
