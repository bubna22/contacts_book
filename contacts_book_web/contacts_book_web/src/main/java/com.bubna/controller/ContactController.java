package com.bubna.controller;

import com.bubna.dao.cmd.Command;
import com.bubna.exception.CustomException;
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

import javax.servlet.annotation.ServletSecurity;
import java.security.Principal;
import java.util.ArrayList;

@Controller
@Import(ModelConfig.class)
public class ContactController {

    @Autowired
    private ApplicationContext applicationContext;

    private com.bubna.model.Model getContactModel() {
        return (com.bubna.model.Model) applicationContext.getBean("contactModel");
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String contactsGet(Principal principal, Model model) {
        User user = (User) applicationContext.getBean("user");
        user.setLogin(principal.getName());
        com.bubna.model.Model contactModel = getContactModel();
        Command cmd = contactModel.getCommand("list");
        cmd.addInput("user", user);
        ArrayList<Contact> contacts = (ArrayList<Contact>) contactModel.executeCommand(cmd);
        model.addAttribute("contacts", contacts);
        return "Contacts";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public String contactsPost(
            @RequestParam(value = "type") String type,
            @RequestParam(value = "action") String action,
            @RequestParam(value = "c_name") String name,
            @RequestParam(value = "c_email", required = false) String email,
            @RequestParam(value = "c_skype", required = false) String skype,
            @RequestParam(value = "c_num", required = false) Integer num,
            @RequestParam(value = "c_telegram", required = false) String telegram,
            @RequestParam(value = "g_name", required = false) String groupName,
            Principal principal, Model model) throws Exception {
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
        Command cmd = contactModel.getCommand(action);
        cmd.addInput("user", user);
        cmd.addInput("entity", contact);
//        throw (Exception) contactModel.executeCommand(cmd);
        if (!contactModel.executeCommand(cmd).equals(Boolean.TRUE)) return "redirect:/main";
        return "redirect:/contacts";
    }

}
