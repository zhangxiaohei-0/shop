package com.qf.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author 张是非
 * @Date 2019/12/4
 */
@Controller
public class SystemExcption implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String systemExcption(HttpServletResponse response){
        int status=response.getStatus();

        switch (status){
            case 400:
                return "400";
            case 401:
                return "401";
            case 402:
                return "402";
            case 403:
                return "403";
            case 404:
                return "404";
        }
        return "myerror";
    }
}
