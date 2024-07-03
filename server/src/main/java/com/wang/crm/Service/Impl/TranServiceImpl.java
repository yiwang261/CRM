package com.wang.crm.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.crm.Constant.Constants;
import com.wang.crm.Mapper.TranMapper;
import com.wang.crm.Pojo.DTO.TranDTO;
import com.wang.crm.Pojo.Entity.Tran;
import com.wang.crm.Pojo.Entity.User;
import com.wang.crm.Pojo.Query.TranQuery;
import com.wang.crm.Service.RedisService;
import com.wang.crm.Service.TranService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TranServiceImpl implements TranService {

    @Resource
    private TranMapper tranMapper;

    @Resource
    private RedisService redisService;

    /**
     * 创建交易
     *
     * @param tranQuery
     * @return
     */
    @Override
    public int saveTran(TranQuery tranQuery) {
        Tran tTran = new Tran();

        //把前端提交过来的参数数据对象TranQuery复制到TTran对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(tranQuery, tTran);

        //交易流水号是不能重复的
        String tranNo = redisService.getOnlyNumber(Constants.REDIS_ONLY_NUMBER_KEY);
        tTran.setTranNo(tranNo);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //创建时间
        tTran.setCreateTime(new Date());
        //创建人id
        tTran.setCreateBy(user.getId());

        return tranMapper.insertSelective(tTran);
    }

    /**
     * 分页查询交易列表数据
     *
     * @param current
     * @return
     */
    @Override
    public PageInfo<TranDTO> getTranByPage(Integer current) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.DEFAULT_PAGE_SIZE);
        //2.查询
        List<TranDTO> list = tranMapper.selectTranPage();
        //3.封装分页数据到PageInfo
        PageInfo<TranDTO> info = new PageInfo<>(list);

        return info;
    }

    /**
     * 查询交易详情
     *
     * @param tranId
     * @return
     */
    @Override
    public TranDTO getTranById(Integer tranId) {
        return tranMapper.selectById(tranId);
    }
}
