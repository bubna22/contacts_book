package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;

public class GroupEntityDAO extends AbstractEntityDAO<Group> {

    GroupEntityDAO() {
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
        auth((User) extraData.get("user"));

        Group inputGroup = (Group) extraData.get("entity");
        session.persist(inputGroup);
    }

    @Override
    public void delete() throws CustomException {
        auth((User) extraData.get("user"));

        Group inputGroup = (Group) extraData.get("entity");
        Criteria criteria = session.createCriteria(Group.class);
        Group outputGroup = (Group) criteria
                .add(Restrictions.eq("name", inputGroup.getName()))
                .uniqueResult();
        session.delete(outputGroup);
    }

    @Override
    public void update() throws CustomException {
        auth((User) extraData.get("user"));

        Group inputGroup = (Group) extraData.get("entity");
        Criteria cCriteria = session.createCriteria(Group.class);
        Group outputGroup = (Group) cCriteria
                .add(Restrictions.eq("name", inputGroup.getName()))
                .uniqueResult();
        outputGroup.setColor(inputGroup.getColor());
    }

    @Override
    public Collection<Group> list() throws CustomException {
        //check access
        auth((User) extraData.get("user"));
        //get contacts list
        return session.createQuery("from Group", Group.class).list();
    }
}
