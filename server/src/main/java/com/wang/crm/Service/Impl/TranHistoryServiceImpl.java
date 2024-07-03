package com.wang.crm.Service.Impl;


import com.wang.crm.Mapper.TranHistoryMapper;
import com.wang.crm.Pojo.Entity.TranHistory;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.TranHistoryQuery;
import com.wang.crm.Service.TranHistoryService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TranHistoryServiceImpl implements TranHistoryService {

    @Resource
    private TranHistoryMapper tranHistoryMapper;

    /**
     * 更新交易历史（阶段）
     *
     * @param tranHistoryQuery
     * @return
     */
    @Override
    public int saveTranHistory(TranHistoryQuery tranHistoryQuery) {
        TranHistory tTranHistory = new TranHistory();

        //把前端提交过来的参数数据对象TranHistoryQuery复制到TTranHistory对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(tranHistoryQuery, tTranHistory);

        //解析jwt得到userId
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //创建时间
        tTranHistory.setCreateTime(new Date());
        //创建人id
        tTranHistory.setCreateBy(user.getId());

        return tranHistoryMapper.insertSelective(tTranHistory);
    }

    /**
     * 查询交易历史/阶段记录
     *
     * @param tranId
     * @return
     */
    @Override
    public List<TranHistory> getTranHistoryByTranId(Integer tranId) {
        return tranHistoryMapper.selectByTranId(tranId);
    }
}
