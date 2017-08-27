package com.bubna.model;

import com.bubna.dao.cmd.*;

import javax.annotation.PostConstruct;

public class EntityModel extends AbstractModel {

    public EntityModel() {
        super();
    }

    @PostConstruct
    public void construct() {
        cmds.put("list", (Command) applicationContext.getBean("listCommand"));
        cmds.put("create", (Command) applicationContext.getBean("createCommand"));
        cmds.put("delete", (Command) applicationContext.getBean("deleteCommand"));
        cmds.put("modify", (Command) applicationContext.getBean("modifyCommand"));
    }

}
