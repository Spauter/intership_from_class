package com.bs.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.bean.ResAdmin;
import com.bs.biz.ResAdminiBiz;
import com.bs.mapper.ResAdminMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ResAdminBizImpl implements ResAdminiBiz{
    @Autowired
    private ResAdminMapper resAdminMapper;

    @Override
    public ResAdmin findByIDAndPwd(String uname, String pwd) {
        QueryWrapper<ResAdmin> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("raname",uname);
        queryWrapper.eq("rapwd",pwd);
        return resAdminMapper.selectOne(queryWrapper);
    }
}
