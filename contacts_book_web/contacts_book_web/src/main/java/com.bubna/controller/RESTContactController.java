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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Import(ModelConfig.class)
@RequestMapping(value = "/api/contact")
public class RESTContactController {

    @Autowired
    private ApplicationContext applicationContext;

    private com.bubna.model.Model getContactModel() {
        return (com.bubna.model.Model) applicationContext.getBean("contactModel");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Contact> contactsGet(Principal principal, Model model) {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(principal.getName());
        com.bubna.model.Model contactModel = getContactModel();
        Command cmd = contactModel.getCommand("list");
        cmd.addInput("user", user);
        return (List<Contact>) contactModel.executeCommand(cmd);
    }

    @RequestMapping(value = "/{c_name}", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void contactsPost(
            @PathVariable(value = "c_name") String name,
            @RequestParam(value = "c_email") String email,
            @RequestParam(value = "c_skype") String skype,
            @RequestParam(value = "c_num") Integer num,
            @RequestParam(value = "c_telegram") String telegram,
            @RequestParam(value = "g_name") String groupName,
            Principal principal,
            Model model) throws Exception {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(principal.getName());
        Contact contact = (Contact) applicationContext.getBean("contact");
        contact.setName(name);
        contact.setEmail(email);
        contact.setSkype(skype);
        contact.setTelegram(telegram);
        contact.setNum(num==null?0:num);
        Group group = (Group) applicationContext.getBean("group");
        group.setName(groupName);
        contact.setGroup(group);
        com.bubna.model.Model contactModel = getContactModel();
        Command cmd = contactModel.getCommand("create");
        cmd.addInput("user", user);
        cmd.addInput("entity", contact);
        contactModel.executeCommand(cmd);
    }

    @RequestMapping(value = "/{c_name}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void contactsPut(
            @RequestParam(value = "c_name") String name,
            Principal principal,
            Model model) throws Exception {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(principal.getName());
        Contact contact = (Contact) applicationContext.getBean("contact");
        contact.setName(name);
        com.bubna.model.Model contactModel = getContactModel();
        Command cmd = contactModel.getCommand("modify");
        cmd.addInput("user", user);
        cmd.addInput("entity", contact);
        contactModel.executeCommand(cmd);
    }

    @RequestMapping(value = "/{c_name}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void contactsDelete(
            @PathVariable(value = "c_name") String name,
            Principal principal,
            Model model) throws Exception {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(principal.getName());
        Contact contact = (Contact) applicationContext.getBean("contact");
        contact.setName(name);
        com.bubna.model.Model contactModel = getContactModel();
        Command cmd = contactModel.getCommand("delete");
        cmd.addInput("user", user);
        cmd.addInput("entity", contact);
        contactModel.executeCommand(cmd);
    }

}
