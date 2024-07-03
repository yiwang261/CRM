package com.wang.crm.Service.Impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.crm.Config.Listener.UploadExcelListener;
import com.wang.crm.Mapper.ClueMapper;
import com.wang.crm.Pojo.DTO.ClueDTO;
import com.wang.crm.Pojo.Entity.Clue;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.BaseQuery;
import com.wang.crm.Service.ClueRemarkService;
import com.wang.crm.Service.ClueService;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {

    @Resource
    private ClueMapper clueMapper;

    @Resource
    private ClueRemarkService clueRemarkService;

    @Transactional
    @Override
    public int deleteClueByActivityId(Integer id) {
        clueRemarkService.deleteByClueId(id);
        return clueMapper.deleteByActivityId(id);

    }

    @Override
    public int deleteClueByActivityIdsBatch(Integer[] activityIds) {
        if(activityIds.length == 0) return 0;
        List<Clue> clueByActivityIds = clueMapper.getClueByActivityIds(activityIds);

        // 获取clueIds
        List<Integer> clueIds = clueByActivityIds.stream().map(clue -> clue.getId()).toList();
        // 删除clueRemark
        clueRemarkService.deleteByClueIdsBatch(clueIds);
        return clueMapper.deleteByActivityIds(activityIds);
    }

    @Override
    public PageInfo<ClueDTO> getClueByPage(BaseQuery baseQuery) {
        PageHelper.startPage(baseQuery.getCurrent(), baseQuery.getSize());
        List<ClueDTO> list = clueMapper.getClueByPage(baseQuery);
        PageInfo<ClueDTO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Integer importExcel(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), Clue.class,
                    new UploadExcelListener(clueMapper)).sheet().doRead();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    @Override
    public boolean checkPhoneExist(String phone) {
        Integer i = clueMapper.selectByPhone(phone);
        return i==0?false:true;
    }

    @Override
    public int addClue(Clue clue) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        clue.setCreateBy(user.getId());
        clue.setCreateTime(new Date());
        return clueMapper.insert(clue);
    }

    @Override
    public Clue getClueById(Integer id) {
        return clueMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateClue(Clue clue) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        clue.setEditTime(new Date());
        clue.setEditBy(user.getId());
        return clueMapper.updateByPrimaryKeySelective(clue);
    }

    @Transactional
    @Override
    public int deleteClueById(Integer id) {
        clueRemarkService.deleteByClueId(id);
        return clueMapper.deleteByPrimaryKey(id);
    }
}
