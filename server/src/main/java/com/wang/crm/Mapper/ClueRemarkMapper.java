package com.wang.crm.Mapper;

import com.wang.crm.Com.DataScope;
import com.wang.crm.Pojo.DTO.ClueRemarkDTO;
import com.wang.crm.Pojo.Entity.ClueRemark;
import com.wang.crm.Pojo.Query.ClueRemarkQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_clue_remark(线索跟踪记录表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.ClueRemark
*/
@Mapper
public interface ClueRemarkMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ClueRemark record);

    int insertSelective(ClueRemark record);

    ClueRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClueRemark record);

    int updateByPrimaryKey(ClueRemark record);

    int deleteByClueId(Integer clueId);
    int deleteByClueIds(List<Integer> clueIds);
    @DataScope(tableAlias = "t_c_r",tableField = "create_by")
    List<ClueRemarkDTO> getClueRemarkDTOListByPage(ClueRemarkQuery clueRemarkQuery);

    List<ClueRemark> getClueRemarkByPage(Integer clueId);
}
