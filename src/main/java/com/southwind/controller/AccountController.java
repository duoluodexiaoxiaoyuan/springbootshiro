package com.southwind.controller;

import com.southwind.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {
    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
        return url;
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model){
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
//            Account account=(Account) subject.getPrincipal();
            Account account=(Account) subject.getPrincipal();
            subject.getSession().setAttribute("account",account);
            return "index";//登录成功回到首页
        } catch (UnknownAccountException e) {  //用户名不存在抛出的异常
            e.printStackTrace();
            model.addAttribute("msg","用户名不存在");
            return "login";//重新登录
        }catch (IncorrectCredentialsException e){  //密码错误抛出的异常
            e.printStackTrace();
            model.addAttribute("msg","密码错误");
            return "login";
        }

    }

    @GetMapping("/unauth")
    @ResponseBody
    public String unauth(){
        return "未授权，无法访问";
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject=SecurityUtils.getSubject();
        subject.logout();  //这样就把session销毁了
        return "index";
    }


}
