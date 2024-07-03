package com.wang.crm.Mapper;

import com.wang.crm.Pojo.DTO.TranDTO;
import com.wang.crm.Pojo.Entity.Tran;

import java.math.BigDecimal;
import java.util.List;

/**
* @author yiwan
* @description 针对表【t_tran(交易表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.Tran
*/
public interface TranMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Tran record);

    int insertSelective(Tran record);

    Tran selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Tran record);

    int updateByPrimaryKey(Tran record);

    BigDecimal getSuccessTranMoney();

    BigDecimal getTotalTranAmount();

    Integer getTranCount();

    Integer getSuccessTranCount();

    List<TranDTO> selectTranPage();

    TranDTO selectById(Integer tranId);
}
