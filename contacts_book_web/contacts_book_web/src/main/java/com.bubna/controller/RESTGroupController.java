package com.bubna.controller;

import com.bubna.dao.cmd.Command;
import com.bubna.model.ModelConfig;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Import(ModelConfig.class)
@RequestMapping(value = "/api/group")
public class RESTGroupController {

    @Autowired
    private ApplicationContext applicationContext;

    private com.bubna.model.Model getGroupModel() {
        return (com.bubna.model.Model) applicationContext.getBean("groupModel");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Group> groupsGet(Principal principal) {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(principal.getName());
        com.bubna.model.Model groupModel = getGroupModel();
        Command cmd = groupModel.getCommand("list");
        cmd.addInput("user", user);
        return (ArrayList<Group>) groupModel.executeCommand(cmd);
    }

    @RequestMapping(value = "/{g_name}", method = RequestMethod.POST)
    public void groupsPost(
            @PathVariable(value = "g_name") String name,
            Principal principal) {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(principal.getName());
        Group group = (Group) applicationContext.getBean("group");
        group.setName(name);
        com.bubna.model.Model groupModel = getGroupModel();
        Command cmd = groupModel.getCommand("create");
        cmd.addInput("user", user);
        cmd.addInput("entity", group);
        groupModel.executeCommand(cmd);
    }

    @RequestMapping(value = "/{g_name}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void groupsPut(
            @PathVariable(value = "g_name") String name,
            @RequestParam(value = "g_color") Integer color,
            Principal principal,
            Model model) throws Exception {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(principal.getName());
        Group group = (Group) applicationContext.getBean("group");
        group.setName(name);
        group.setColor(color);
        com.bubna.model.Model groupModel = getGroupModel();
        Command cmd = groupModel.getCommand("modify");
        cmd.addInput("user", user);
        cmd.addInput("entity", group);
        groupModel.executeCommand(cmd);
    }

    @RequestMapping(value = "/{g_name}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void groupsDelete(
            @PathVariable(value = "g_name") String name,
            Principal principal,
            Model model) throws Exception {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(principal.getName());
        Group group = (Group) applicationContext.getBean("group");
        group.setName(name);
        com.bubna.model.Model groupModel = getGroupModel();
        Command cmd = groupModel.getCommand("delete");
        cmd.addInput("user", user);
        cmd.addInput("entity", group);
        groupModel.executeCommand(cmd);
    }

}
