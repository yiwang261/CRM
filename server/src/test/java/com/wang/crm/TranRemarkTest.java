package com.wang.crm;

import com.github.pagehelper.PageInfo;
import com.wang.crm.Pojo.Entity.TranRemark;
import jakarta.annotation.Resource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TranRemarkTest {
    @Resource
    private com.wang.crm.Service.TranRemarkService tranRemarkService;
    @org.junit.jupiter.api.Test
    public void test1() {
        PageInfo<TranRemark> tranRemarkByPage = tranRemarkService.getTranRemarkByPage(1, 5);
        System.out.println(tranRemarkByPage);
    }
}
