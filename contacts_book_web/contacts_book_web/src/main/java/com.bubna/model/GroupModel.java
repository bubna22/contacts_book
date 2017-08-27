package com.bubna.model;

import com.bubna.dao.EntityDAO;

import javax.annotation.PostConstruct;

public class GroupModel extends EntityModel {

    GroupModel() {
        super();
    }

    @PostConstruct
    public void construct() {
        super.construct();
        this.entityDao = (EntityDAO) applicationContext.getBean("groupDAO");
    }

}
