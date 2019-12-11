package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @Author 张是非
 * @Date 2019/12/4
 */
public interface GoodsService {

    List<Goods> list();

    int insert(Goods goods);
}
