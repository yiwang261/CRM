package com.wang.crm.Mapper;

import com.wang.crm.Pojo.Entity.TranRemark;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_tran_remark(交易跟踪记录表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.TranRemark
*/
public interface TranRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TranRemark record);

    int insertSelective(TranRemark record);

    TranRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TranRemark record);

    int updateByPrimaryKey(TranRemark record);

    List<TranRemark> selectTranRemarkPage(Integer tranId);
}
