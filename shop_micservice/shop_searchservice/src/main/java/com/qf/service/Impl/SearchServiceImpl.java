package com.qf.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author 张是非
 * @Date 2019/12/9
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public int insertSolr(Goods goods) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id",goods.getId()+"");
        document.addField("subject",goods.getSubject());
        document.addField("info",goods.getInfo());
        document.addField("price",goods.getPrice().doubleValue());
        document.addField("save",goods.getSave());
        document.addField("image",goods.getFmurl());

        try {
            solrClient.add(document);
            //增删改都必须commit
            solrClient.commit();
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Goods> querySolr(String keyword) {
        System.out.println(keyword);
        SolrQuery query = new SolrQuery();

        //惊醒分页设置
        query.setStart(0);
        query.setRows(3);
        //设置高亮
        query.setHighlight(true);
        query.setHighlightSimplePre("<font color='red'>");//前缀
        query.setHighlightSimplePost("</font>");//后缀
        query.addHighlightField("subject");//哪个内容需要高亮
//        query.setHighlightSnippets(3);//高亮折叠的次数
//        query.setHighlightFragsize(10);//高亮折叠的大小


        if (keyword != null && !keyword.equals("")){

            query.setQuery("subject:"+keyword+" || info:" +keyword);
        }else {
            query.setQuery("*:*");
        }

        try {
           QueryResponse response =solrClient.query(query);

           //获得高亮后的结果
            //Map<id, 高亮信息>
            //高亮信息--》Map<需要高亮的字段, 高亮内容的集合>
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            //获得搜索结果
           SolrDocumentList results =response.getResults();

            //符合关键词的记录条数
            long numFound = results.getNumFound();

           List<Goods> goodsList = new ArrayList<>();
            for (SolrDocument document : results) {
                //document==>查询出goods对象记录
                Goods goods = new Goods();
                goods.setId(Integer.parseInt((String)document.get("id")));
                goods.setSubject(document.get("subject") + "")
                        .setSave((int)document.get("save"))
                        .setPrice(BigDecimal.valueOf((double)document.get("price")))
                        .setFmurl(document.get("image") + "");

                //处理高亮结果
                if(highlighting.containsKey(goods.getId()+"")){
                    //说明当前又高亮信息
                    //依次获取高亮的字段
                    Map<String, List<String>> stringListMap =highlighting.get(goods.getId()+"");
                    List<String> subject = stringListMap.get("subject");
                    if (subject !=null){
                        goods.setSubject(subject.get(0));
                    }
                }
                goodsList.add(goods);
            }
            System.out.println("集合长度"+goodsList.size());
            return goodsList;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
