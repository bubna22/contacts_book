package com.bubna.model;

import com.bubna.dao.EntityDAO;

import javax.annotation.PostConstruct;

public class GroupModel extends EntityModel {

    @PostConstruct
    private void construct() {
        this.entityDao = (EntityDAO) applicationContext.getBean("groupDAO");
    }

}
