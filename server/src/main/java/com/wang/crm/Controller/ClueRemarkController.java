package com.wang.crm.Controller;

import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.DTO.ClueRemarkDTO;
import com.wang.crm.Pojo.Entity.ClueRemark;
import com.wang.crm.Pojo.Query.ClueRemarkQuery;
import com.wang.crm.Result.Result;
import com.wang.crm.Service.ClueRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClueRemarkController {
    @Resource
    private ClueRemarkService clueRemarkService;
    @PostMapping("/clue/remark")
    public Result addClueRemark(@RequestBody ClueRemark remark) {
        System.out.println("addClueRemark: " + remark);
        int i = clueRemarkService.addClueRemark(remark);

        return i==1?Result.success():Result.error();
    }

    @GetMapping("/clue/remark")
    public Result<PageInfo<ClueRemarkDTO>> getClueRemarkList(ClueRemarkQuery clueRemarkQuery) {
        PageInfo<ClueRemarkDTO> clueRemarkList = clueRemarkService.getClueRemarkDTOListByPage(clueRemarkQuery);
        return Result.success(clueRemarkList);
    }

    @PutMapping("/clue/remark")
    public Result updateClueRemark(@RequestBody ClueRemark remark) {
        System.out.println("updateClueRemark: " + remark);
        int i = clueRemarkService.updateClueRemark(remark);
//        int i =  0;
        return i==1?Result.success():Result.error();
    }
    @DeleteMapping("/clue/remark/{id}")
    public Result deleteClueRemark(@PathVariable Integer id) {
        int i = clueRemarkService.deleteById(id);
        return i==1?Result.success():Result.error();
    }
    @GetMapping(value = "/clue/{clueId}/remark")
    public Result<PageInfo<ClueRemark>> clueRemarkPage(@PathVariable(value = "clueId") Integer clueId,
                            @RequestParam(value = "current",defaultValue = "1") Integer current) {

        PageInfo<ClueRemark> pageInfo = clueRemarkService.getClueRemarkByPage(current, clueId);
        return Result.success(pageInfo);
    }
}
