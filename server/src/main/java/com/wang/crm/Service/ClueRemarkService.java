package com.wang.crm.Service;

import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.DTO.ClueRemarkDTO;
import com.wang.crm.Pojo.Entity.ClueRemark;
import com.wang.crm.Pojo.Query.ClueRemarkQuery;

import java.util.List;

public interface ClueRemarkService {
    int deleteByClueId(Integer id);
    int deleteByClueIdsBatch(List<Integer> ids);

    int addClueRemark(ClueRemark remark);

    PageInfo<ClueRemarkDTO> getClueRemarkDTOListByPage(ClueRemarkQuery clueRemarkQuery);

    int updateClueRemark(ClueRemark remark);

    int deleteById(Integer id);

    PageInfo<ClueRemark> getClueRemarkByPage(Integer current, Integer clueId);
}
