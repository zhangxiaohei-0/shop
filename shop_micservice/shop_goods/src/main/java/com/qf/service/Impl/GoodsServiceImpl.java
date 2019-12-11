package com.qf.service.Impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.entity.GoodsImages;
import com.qf.mapper.GoodsImageMapper;
import com.qf.mapper.GoodsMapper;
import com.qf.service.GoodsService;
import com.qf.service.SearchService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author 张是非
 * @Date 2019/12/4
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Reference
    private SearchService searchService;

    @Autowired
    private GoodsImageMapper goodsImageMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<Goods> list() {

        return goodsMapper.queryList();
    }

    @Override
    @Transactional
    public int insert(Goods goods) {

        goodsMapper.insert(goods);

        GoodsImages goodsImages=new GoodsImages()
                                .setGid(goods.getId())
                                .setIsfengmian(1)
                                .setUrl(goods.getFmurl());
        goodsImageMapper.insert(goodsImages);

        for ( String otherurl:goods.getOtherurls()) {
            GoodsImages goodsImages1 = new GoodsImages()
                                        .setGid(goods.getId())
                                        .setIsfengmian(0)
                                        .setUrl(otherurl);
            goodsImageMapper.insert(goodsImages1);
        }
//        //同步索引库
//        searchService.insertSolr(goods);

        //将添加索引库的消息发送到MQ中
        rabbitTemplate.convertAndSend("goods_exchange","",goods);

        return 1;
    }
}
