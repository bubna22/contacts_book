package com.bubna.controller;

import com.bubna.dao.cmd.Command;
import com.bubna.model.ModelConfig;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@Import(ModelConfig.class)
public class UserController {

    @Autowired
    private ApplicationContext applicationContext;

    private com.bubna.model.Model getUserModel() {
        return (com.bubna.model.Model) applicationContext.getBean("userModel");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "pass") String pass,
            @CookieValue(value = "JSESSIONID") String sessionId,
            HttpServletResponse response) {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(login);
        user.setPass(pass);
        user.setIp("1");
        com.bubna.model.Model userModel = getUserModel();
        Command cmd = userModel.getCommand("login");
        cmd.addInput("user", user);
        if (!userModel.executeCommand(cmd).equals(Boolean.TRUE)) return "redirect:/";
        response.addCookie(new Cookie("user", login));
        return "redirect:/contacts";
    }

}
