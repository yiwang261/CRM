package com.wang.crm.Service;


import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.DTO.ClueDTO;
import com.wang.crm.Pojo.Entity.Clue;
import com.wang.crm.Pojo.Query.BaseQuery;
import org.springframework.web.multipart.MultipartFile;

public interface ClueService {
    int deleteClueByActivityId(Integer id);

    int deleteClueByActivityIdsBatch(Integer[] ids);

    PageInfo<ClueDTO> getClueByPage(BaseQuery baseQuery);

    Integer importExcel(MultipartFile multipartFile);

    boolean checkPhoneExist(String phone);

    int addClue(Clue clue);

    Clue getClueById(Integer id);

    int updateClue(Clue clue);

    int deleteClueById(Integer id);
}
