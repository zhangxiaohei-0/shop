package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @Author 张是非
 * @Date 2019/12/9
 */
public interface SearchService {

    /**
     * 添加商品到索引库
     * @param goods
     * @return
     */
    int insertSolr(Goods goods);

    /**
     * 根据关键字搜索商品
     * @param keyword
     * @return
     */
    List<Goods> querySolr(String keyword);

}
