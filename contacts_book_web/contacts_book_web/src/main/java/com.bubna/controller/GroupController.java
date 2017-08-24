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

@Controller
@Import(ModelConfig.class)
public class GroupController {

    @Autowired
    private ApplicationContext applicationContext;

    private com.bubna.model.Model getGroupModel() {
        return (com.bubna.model.Model) applicationContext.getBean("groupModel");
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String groupsGet(@CookieValue(value = "user") String userName, Model model) {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(userName);
        com.bubna.model.Model groupModel = getGroupModel();
        Command cmd = groupModel.getCommand("list");
        cmd.addInput("user", user);
        model.addAttribute("groups",
                groupModel.executeCommand(cmd));
        return "Groups";
    }

    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    public String groupsPost(
            @RequestParam(value = "type") String type,
            @RequestParam(value = "action") String action,
            @RequestParam(value = "g_name") String name,
            @RequestParam(value = "g_color", required = false) Integer color,
            @CookieValue(value = "user") String userName,
            Model model) {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(userName);
        Group group = (Group) applicationContext.getBean("group");
        group.setName(name);
        group.setColor(color);
        com.bubna.model.Model groupModel = getGroupModel();
        Command cmd = groupModel.getCommand(action);
        cmd.addInput("user", user);
        cmd.addInput("entity", group);
        if (!groupModel.executeCommand(cmd).equals(Boolean.TRUE)) return "redirect:/";
        return "redirect:/contacts";
    }

}
