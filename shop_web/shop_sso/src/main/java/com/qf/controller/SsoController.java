package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.User;
import com.qf.service.SsoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 张是非
 * @Date 2019/12/11
 */
@Controller
@RequestMapping("/sso")
public class SsoController {

    @Reference
    private SsoService ssoService;

    @RequestMapping("/tologin")
    public String login(){
        return "login";
    }

    @RequestMapping("/toregister")
    public String register(){
        return "register";
    }

    @RequestMapping("/register")
    public String UserRegister(User user, Model model){

        int register = ssoService.register(user);
        if (register>0){
            return "login";
        }else if (register==-1){
            model.addAttribute("error","用户名已存在");
        }else {
            model.addAttribute("error","注册失败");
        }

        return "register";
    }
}
