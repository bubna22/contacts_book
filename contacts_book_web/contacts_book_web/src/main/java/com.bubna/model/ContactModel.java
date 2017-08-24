package com.bubna.model;

import com.bubna.dao.EntityDAO;

import javax.annotation.PostConstruct;

public class ContactModel extends EntityModel {

    @PostConstruct
    private void construct() {
        this.entityDao = (EntityDAO) applicationContext.getBean("contactDAO");
    }

}
