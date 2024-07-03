package com.wang.crm.Mapper;

import com.wang.crm.Pojo.Entity.TranHistory;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_tran_history(交易历史记录表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.TranHistory
*/
public interface TranHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TranHistory record);

    int insertSelective(TranHistory record);

    TranHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TranHistory record);

    int updateByPrimaryKey(TranHistory record);

    List<TranHistory> selectByTranId(Integer tranId);
}
