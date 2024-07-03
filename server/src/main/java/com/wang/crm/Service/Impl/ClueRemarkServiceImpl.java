package com.wang.crm.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.crm.Constant.Constants;
import com.wang.crm.Mapper.ClueRemarkMapper;
import com.wang.crm.Pojo.DTO.ClueRemarkDTO;
import com.wang.crm.Pojo.Entity.ClueRemark;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.ClueRemarkQuery;
import com.wang.crm.Service.ClueRemarkService;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Resource
    private ClueRemarkMapper clueRemarkMapper;

    @Override
    public int deleteByClueId(Integer id) {

        return clueRemarkMapper.deleteByClueId(id);
    }

    @Override
    public int deleteByClueIdsBatch(List<Integer> ids) {
        return clueRemarkMapper.deleteByClueIds(ids);
    }

    @Override
    public int addClueRemark(ClueRemark remark) {
        if (remark==null) return 0;
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        remark.setCreateTime(new Date());
        remark.setCreateBy(user.getId());

        return clueRemarkMapper.insert(remark);
    }

    @Override
    public PageInfo<ClueRemarkDTO> getClueRemarkDTOListByPage(ClueRemarkQuery clueRemarkQuery) {
        PageHelper.startPage(clueRemarkQuery.getCurrent(),clueRemarkQuery.getSize());

        List<ClueRemarkDTO> clueRemarkList =clueRemarkMapper.getClueRemarkDTOListByPage(clueRemarkQuery);
        PageInfo<ClueRemarkDTO> pageInfo = new PageInfo<>(clueRemarkList);

        return pageInfo;
    }

    @Override
    public int updateClueRemark(ClueRemark remark) {
        remark.setEditTime(new Date());
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        remark.setEditBy(user.getId());
        return clueRemarkMapper.updateByPrimaryKeySelective(remark);
    }

    @Override
    public int deleteById(Integer id) {
        return clueRemarkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<ClueRemark> getClueRemarkByPage(Integer current, Integer clueId) {
        PageHelper.startPage(current, Constants.DEFAULT_PAGE_SIZE);
        List<ClueRemark>clueRemarkList = clueRemarkMapper.getClueRemarkByPage(clueId);

        return new PageInfo<>(clueRemarkList);
    }
}
