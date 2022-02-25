package com.cqu.lxh.client.controller;

import com.cqu.lxh.client.model.User;
import com.cqu.lxh.client.service.SSOClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

// TODO 跨域问题也要解决
@Controller
public class UserController {
    final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String KEY_USER = "__user__";

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleUnknowException(Exception ex) {
        return new ModelAndView("500.html", Map.of("error", ex.getClass().getSimpleName(), "message", ex.getMessage()));
    }

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("redirect:"+SSOClientService.SERVER_URL_PREFIX+"/");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("redirect:"+SSOClientService.SERVER_URL_PREFIX+"/register");
    }

    @PostMapping("/register")
    public ModelAndView doRegister() {
        return new ModelAndView("redirect:"+SSOClientService.SERVER_URL_PREFIX+"/register");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("redirect:"+SSOClientService.SERVER_URL_PREFIX+"/login");
    }

    @PostMapping("/login")
    public ModelAndView doLogin() {
        return new ModelAndView("redirect:"+SSOClientService.SERVER_URL_PREFIX+"/login");
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        return new ModelAndView("redirect:"+SSOClientService.SERVER_URL_PREFIX+"/profile");
    }

    @GetMapping("/logout_remote")
    public ModelAndView logoutRemote() {
        // 点击view按钮调用的logout实际上是这个
        return new ModelAndView("redirect:"+SSOClientService.SERVER_URL_PREFIX+"/logout");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 注意这个接口是由ssoServer调用的
        logger.info("log out local session");
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String doLogout(HttpSession session) {
        // 注意这个接口是由ssoServer调用的
        logger.info("log out local session");
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/ainfo")
    public ModelAndView ainfo(HttpSession session) {
        User user = (User) session.getAttribute(KEY_USER);
        Map<String, Object> model = new HashMap<>();
        if (user != null)
            model.put("user", model);
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if (isLogin != null && isLogin)
            model.put("isLogin", isLogin);
        return new ModelAndView("A_info.html", model);
    }

    @GetMapping("/binfo")
    public ModelAndView binfo() {
        return new ModelAndView("redirect:"+SSOClientService.CLIENTB_HOST_URL+"/binfo");
    }
}