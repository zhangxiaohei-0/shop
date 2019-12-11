package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.entity.ResultData;
import com.qf.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author 张是非
 * @Date 2019/12/3
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    //设置上传的路径
    private String uploadPath="C:/Users/pc/Pictures/Saved Pictures";

    @Reference
    private GoodsService goodsService;
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping("/{list}")
    public String list(Model model){
        List<Goods> goods=goodsService.list();
        model.addAttribute("goodsList",goods);
        return "goodslist";
    }

    @RequestMapping("/uploader")
    @ResponseBody
    public ResultData<String> uploader(MultipartFile file){

        String path=null;
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    "jpg",
                    null
            );
            path=storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        //文件名
//        String fileName = UUID.randomUUID().toString()+file.getOriginalFilename();
//        String path = uploadPath+"/"+fileName;
//        try (
//                InputStream inputStream = file.getInputStream();
//                OutputStream outputStream = new FileOutputStream(path);
//        ){
//            IOUtils.copy(inputStream,outputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setData("http://www.img.com:8080/" + path);
    }

    //回显
//    @RequestMapping("/showimg")
//    public void showImage(String imgPath, HttpServletResponse response){
//
//        try (
//                InputStream inputStream = new FileInputStream(imgPath);
//                ServletOutputStream outputStream = response.getOutputStream();
//        ){
//
//            IOUtils.copy(inputStream,outputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //添加商品
    @RequestMapping("/insert")
    public String insert(Goods goods){

        goodsService.insert(goods);
        return "redirect:/goods/list";
    }

//    @RequestMapping("/ajax")
//    @ResponseBody
//    public ResultData<String> ajax(){
//        System.out.println("收到ajax请求");
//        System.out.println(1 / 0);
//        return new ResultData<String>()
//                .setCode(ResultData.ResultCodeList.OK)
//                .setData("MyData");
//    }

}
