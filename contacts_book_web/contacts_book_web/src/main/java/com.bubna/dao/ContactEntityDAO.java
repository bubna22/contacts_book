package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;

public class ContactEntityDAO extends AbstractEntityDAO<Contact> {

    ContactEntityDAO() {
        super();
    }

    private void auth(User u) throws CustomException {
        Criteria criteria = session.createCriteria(User.class);
        User outputUser = (User) criteria
                .add(Restrictions.eq("login", u.getLogin()))
                .uniqueResult();
        if (outputUser == null) throw new CustomException("auth failed");
    }

    @Override
    public void create() throws CustomException {
        //check access
        auth((User) extraData.get("user"));
        Contact inputContact = (Contact) extraData.get("entity");
        session.persist(inputContact);
    }

    @Override
    public void delete() throws CustomException {
        //check access
        auth((User) extraData.get("user"));

        Contact inputContact = (Contact) extraData.get("entity");
        Criteria criteria = session.createCriteria(Contact.class);
        Contact outputContact = (Contact) criteria
                .add(Restrictions.eq("name", inputContact.getName()))
                .uniqueResult();
        session.delete(outputContact);
    }

    @Override
    public void update() throws CustomException {
        //check access
        auth((User) extraData.get("user"));

        Contact inputContact = (Contact) extraData.get("entity");
        Criteria cCriteria = session.createCriteria(Contact.class);
        Contact outputContact = (Contact) cCriteria
                .add(Restrictions.eq("name", inputContact.getName()))
                .uniqueResult();
        outputContact.setEmail(inputContact.getEmail());
        outputContact.setTelegram(inputContact.getTelegram());
        outputContact.setNum(inputContact.getNum());
        outputContact.setSkype(inputContact.getSkype());
        if (inputContact.getGroup().getName().equals("")) inputContact.getGroup().setName(null);
        if (inputContact.getGroup().getName() != null) {
            Criteria gCriteria = session.createCriteria(Group.class);
            Group group = (Group) gCriteria
                    .add(Restrictions.eq("name", inputContact.getGroup().getName()))
                    .uniqueResult();
            if (group == null) throw new CustomException("no such group");
            outputContact.setGroup(group);
        }
    }

    @Override
    public Collection<Contact> list() throws CustomException {
        //check access
        auth((User) extraData.get("user"));
        //get contacts list
        return session.createQuery("from Contact", Contact.class).list();
    }
}
