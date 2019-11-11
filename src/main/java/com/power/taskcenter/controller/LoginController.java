package com.power.taskcenter.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.power.taskcenter.entity.User;
import com.power.taskcenter.util.JwtTokenUtils;
import com.power.taskcenter.util.MD5Encode;
import com.power.taskcenter.util.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
@Component
public class LoginController {

    @Value("${spring.dynamictask.login-username}")
    private String loginUsername;

    @Value("${spring.dynamictask.login-password}")
    private String loginPassword;

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultBean login(User user) {
        if (ObjectUtil.isNull(user)){
            return   ResultBean.error("参数不正确");
        }
        if(!StrUtil.equals(user.getUsername(),loginUsername)){
            return  ResultBean.error("用户名不正确");
        }

        if(!StrUtil.equals(MD5Encode.MD5Encode(user.getPassword()),loginPassword.toUpperCase())){
            return ResultBean.error("密码不正确");
        }
       String token = JwtTokenUtils.generateToken(user.getUsername());
        log.info(token);
        return ResultBean.successData(token);
    }


    @GetMapping(value = {"/", "/main"})
    public String main(Model model) {
        return "index";
    }


    @GetMapping("/welcome")
    public String welcome(Model model) {
        return "welcome";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:login";
    }

    @GetMapping("/taskList")
    public String index() {
        return "task-list";
    }

    @GetMapping("/taskAdd")
    public String add() {
        return "task-add";
    }

    @GetMapping("/logsList")
    public String logsList() {
        return "logs-list";
    }

}
