package com.qf.controller;

import com.qf.entity.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 张是非
 * @Date 2019/12/3
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public Object ExcptionHadle(HttpServletRequest request, Exception e){
        System.out.println("项目出现异常"+e.getMessage());

        String heard = request.getHeader("X-Request-With");
        if (heard != null && heard.equals("XMLHttpRequest")){
            //说明当前是一个ajax请求
            return new ResultData<>()
                    .setCode(ResultData.ResultCodeList.ERROR)
                    .setMsg("服务器繁忙，请稍后再试");
        }else {
            return new ModelAndView("myerror");
        }

    }
}
