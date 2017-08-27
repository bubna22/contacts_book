package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

public class ContactEntityDAO extends AbstractEntityDAO<Contact> {

    ContactEntityDAO() {
        super();
    }

    private User auth(User u) throws CustomException {
        User outputUser = entityManager
                .createQuery("select u from User u where u.login = :user_login",
                User.class).setParameter("user_login", u.getLogin()).getSingleResult();
        if (outputUser == null) throw new CustomException("auth failed");
        return outputUser;
    }

    @Override
    @Transactional
    public void create() throws CustomException {
        //check access
        User outputUser = auth((User) extraData.get("user"));
        Contact inputContact = (Contact) extraData.get("entity");
        Contact outputContact = entityManager.merge(inputContact);
        entityManager.createNativeQuery("INSERT INTO contact_user (contact_id, user_id) " +
                "values (" + outputContact.getId() + ", " + outputUser.getId() + ")").executeUpdate();
    }

    @Override
    @Transactional
    public void delete() throws CustomException {
        //check access
        User outputUser = auth((User) extraData.get("user"));

        Contact inputContact = (Contact) extraData.get("entity");
        Contact outputContact = entityManager
                .createQuery("select c from Contact c where c.name = :contact_name",
                        Contact.class).setParameter("contact_name", inputContact.getName()).getSingleResult();
        entityManager.remove(outputContact);
        entityManager.createNativeQuery("DELETE FROM contact_user WHERE " +
                "user_id = " + outputUser.getId() + " AND contact_id = " + outputContact.getId()).executeUpdate();
    }

    @Override
    @Transactional
    public void update() throws CustomException {
        //check access
        auth((User) extraData.get("user"));

        Contact inputContact = (Contact) extraData.get("entity");
        Contact outputContact = entityManager
                .createQuery("select c from Contact c where c.name = :contact_name",
                        Contact.class).setParameter("contact_name", inputContact.getName()).getSingleResult();
        outputContact.setEmail(inputContact.getEmail());
        outputContact.setTelegram(inputContact.getTelegram());
        outputContact.setNum(inputContact.getNum());
        outputContact.setSkype(inputContact.getSkype());
        if (inputContact.getGroup().getName().equals("")) inputContact.getGroup().setName(null);
        if (inputContact.getGroup().getName() != null) {
            Group group = entityManager
                    .createQuery("select g from Group g where g.name = :group_name",
                            Group.class).setParameter("group_name", inputContact.getGroup().getName()).getSingleResult();
            if (group == null) throw new CustomException("no such group");
            outputContact.setGroup(group);
        }
        entityManager.merge(outputContact);
    }

    @Override
    @Transactional
    public Collection<Contact> list() throws CustomException {
        //check access
        auth((User) extraData.get("user"));
        //get contacts list
        return entityManager.createQuery("from Contact", Contact.class).getResultList();
    }
}
