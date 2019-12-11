package com.qf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Goods;

import java.util.List;

/**
 * @Author 张是非
 * @Date 2019/12/4
 */
public interface GoodsMapper extends BaseMapper<Goods> {


    List<Goods> queryList();

    Goods queryById(Integer gid);
}
