package com.wang.crm.Controller;

import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.DTO.ClueDTO;
import com.wang.crm.Pojo.Entity.Clue;
import com.wang.crm.Pojo.Query.BaseQuery;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.ClueService;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ClueController {
    @Resource
    private ClueService clueService;

    @PreAuthorize("hasAnyAuthority('clue:list')")
    @GetMapping("/clue")
    public Result<PageInfo<ClueDTO>> getClueList(BaseQuery baseQuery){
        if(baseQuery.getCurrent()==null){
            baseQuery.setCurrent(1);
        }
        if(baseQuery.getSize()==null){
            baseQuery.setSize(10);
        }
        PageInfo<ClueDTO> pageInfo = clueService.getClueByPage(baseQuery);
        return Result.success(pageInfo);
    }
    @PreAuthorize("hasAnyAuthority('clue:view')")
    @GetMapping("/clue/{id}")
    public Result<Clue> getClueById(@PathVariable("id") Integer id){
        Clue clue = clueService.getClueById(id);
        return Result.success(clue);
    }

    @GetMapping("/clue/checkPhoneExist/{phone}")
    public Result checkPhoneExist(@PathVariable("phone") String phone){
        if(clueService.checkPhoneExist(phone)){
            return Result.success();
        }
        return Result.error();
    }
    @PreAuthorize("hasAnyAuthority('clue:add')")
    @PostMapping("/clue")
    public Result addClue(@RequestBody Clue clue){
        int i = clueService.addClue(clue);

        return i==0? Result.error():Result.success();
    }
    @PreAuthorize("hasAnyAuthority('clue:edit')")
    @PutMapping("/clue")
    public Result updateClue(@RequestBody Clue clue){
        int i = clueService.updateClue(clue);

        return i==0? Result.error():Result.success();
    }

    @PreAuthorize("hasAnyAuthority('clue:delete')")
    @DeleteMapping("/clue/{id}")
    public Result deleteClue(@PathVariable("id") Integer id){
        int i = clueService.deleteClueById(id);
        return i==0? Result.error("已转化为客户，无法删除"):Result.success();
    }

    @PreAuthorize("hasAnyAuthority('clue:import')")
    @PostMapping("/clue/importExcel")
    public Result upload(@RequestParam("file") MultipartFile multipartFile){
        if(multipartFile.isEmpty()) return Result.error("文件为空");
        Integer success = clueService.importExcel(multipartFile);
        return success==0?Result.error("导入失败"):Result.success("导入成功");
    }

}
