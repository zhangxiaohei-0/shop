package com.qf.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author 张是非
 * @Date 2019/12/9
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private SearchService searchService;

    @RequestMapping("/searchByKeyword")
    public String searchByKeyWord(String keyword, Model model){

         System.out.println("需要搜索的关键字"+keyword);
         List<Goods> goods = searchService.querySolr(keyword);
         for ( Goods g:goods) {
             System.out.println(g);
         }
         model.addAttribute("goods",goods);
         return "searchlist";
    }
}
