package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 张是非
 * @Date 2019/12/12
 */
@Controller
@RequestMapping("/forget")
public class ForgetController {



    /**
     * 跳转到忘记密码的页面
     * @return
     */
    @RequestMapping("/toforgetpassword")
    public String toforgetpassword(){
        return "forgetpassword";
    }

    /**
     * 发送找回密码的邮件
     * @return
     */
    @RequestMapping("/sendmail")
    public String sendPasswordMail(String username){

        //通过用户找到用户信息

        return null;
    }
}
