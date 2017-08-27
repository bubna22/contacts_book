package com.bubna.model;

import com.bubna.dao.EntityDAO;

import javax.annotation.PostConstruct;

public class ContactModel extends EntityModel {

    ContactModel() {
        super();
    }

    @PostConstruct
    public void construct() {
        super.construct();
        this.entityDao = (EntityDAO) applicationContext.getBean("contactDAO");
    }

}
