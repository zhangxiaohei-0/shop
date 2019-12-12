package com.qf.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.entity.User;
import com.qf.mapper.SsoMapper;
import com.qf.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author 张是非
 * @Date 2019/12/11
 */
@Service
public class SsoServiceImpl implements SsoService {

    @Autowired
    private SsoMapper ssoMapper;

    @Override
    public int register(User user) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        Integer count = ssoMapper.selectCount(queryWrapper);
        if (count==0){
            return ssoMapper.insert(user);
        }else {
            return -1;
        }
    }
}
