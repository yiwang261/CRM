package com.wang.crm.Service;


import com.wang.crm.Pojo.Entity.TranHistory;
import com.wang.crm.Pojo.Query.TranHistoryQuery;

import java.util.List;

public interface TranHistoryService {

    int saveTranHistory(TranHistoryQuery tranHistoryQuery);

    List<TranHistory> getTranHistoryByTranId(Integer tranId);
}
