package com.bubna.controller;

import com.bubna.model.ModelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Import(ModelConfig.class)
public class AdminToolsController {

    @Autowired
    private ApplicationContext applicationContext;

    private com.bubna.model.Model getAdminModel() {
        return (com.bubna.model.Model) applicationContext.getBean("adminModel");
    }

    @RequestMapping(value = "/inactive_users", method = RequestMethod.GET)
    public String inactiveUsers(Model model) {
        com.bubna.model.Model adminModel = getAdminModel();
        model.addAttribute("inactiveUsers",
                adminModel.executeCommand(adminModel.getCommand("inactiveUsers")));
        return "InactiveUsers";
    }

    @RequestMapping(value = "/users_count", method = RequestMethod.GET)
    public String usersCount(Model model) {
        com.bubna.model.Model adminModel = getAdminModel();
        model.addAttribute("count",
                adminModel.executeCommand(adminModel.getCommand("count")));
        return "UserCount";
    }

    @RequestMapping(value = "/user_contacts_count", method = RequestMethod.GET)
    public String userContactsCount(Model model) {
        com.bubna.model.Model adminModel = getAdminModel();
        model.addAttribute("userContactsCount",
                adminModel.executeCommand(adminModel.getCommand("userContactsCount")));
        return "UserContactsCount";
    }

    @RequestMapping(value = "/user_groups_count", method = RequestMethod.GET)
    public String userGroupsCount(Model model) {
        com.bubna.model.Model adminModel = getAdminModel();
        model.addAttribute("userGroupsCount",
                adminModel.executeCommand(adminModel.getCommand("userGroupsCount")));
        return "UserGroupsCount";
    }

    @RequestMapping(value = "/user_avg_groups_count", method = RequestMethod.GET)
    public String userAVGGroupsCount(Model model) {
        com.bubna.model.Model adminModel = getAdminModel();
        model.addAttribute("userGroupsAVGCount",
                adminModel.executeCommand(adminModel.getCommand("userGroupsAVGCount")));
        return "UserAVGGroupsCount";
    }

    @RequestMapping(value = "/user_avg_contacts_count", method = RequestMethod.GET)
    public String userAVGContactsCount(Model model) {
        com.bubna.model.Model adminModel = getAdminModel();
        model.addAttribute("userContactsAVGCount",
                adminModel.executeCommand(adminModel.getCommand("userContactsAVGCount")));
        return "UserAVGContactsCount";
    }
}
