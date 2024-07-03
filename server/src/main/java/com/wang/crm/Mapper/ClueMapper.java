package com.wang.crm.Mapper;

import com.wang.crm.Pojo.DTO.ClueDTO;
import com.wang.crm.Pojo.Entity.Clue;
import com.wang.crm.Pojo.Query.BaseQuery;
import com.wang.crm.Result.ChartsData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author yiwan
* @description 针对表【t_clue(线索表)】的数据库操作Mapper
* @createDate 2024-05-02 15:52:54
* @Entity com.wang.crm.Pojo.Entity.Clue
*/
@Mapper
public interface ClueMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Clue record);

    int insertSelective(Clue record);

    Clue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Clue record);

    int updateByPrimaryKey(Clue record);

    //通过活动id批量删除线索
    int deleteByActivityIds(Integer[] activityIds);
    //通过活动id删除线索
    int deleteByActivityId(Integer activityId);

    //通过活动id查询线索
    List<Clue> getClueByActivityId(Integer activityId);
    //通过活动ids批量查询线索
    List<Clue> getClueByActivityIds(Integer[] activityIds);

    List<ClueDTO> getClueByPage(BaseQuery baseQuery);

    //importFromExcel
    void save(List<Clue> cachedDataList);

    Integer selectByPhone(String phone);

    Integer getClueCount();

    List<ChartsData> getClueSourceList();
}
