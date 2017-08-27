package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public class GroupEntityDAO extends AbstractEntityDAO<Group> {

    GroupEntityDAO() {
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
        User outputUser = auth((User) extraData.get("user"));
        Group inputGroup = (Group) extraData.get("entity");
        Group outputGroup = entityManager.merge(inputGroup);
        entityManager.createNativeQuery("INSERT INTO group_user (group_id, user_id) " +
                "values (" + outputGroup.getId() + ", " + outputUser.getId() + ")").executeUpdate();
    }

    @Override
    @Transactional
    public void delete() throws CustomException {
        User outputUser = auth((User) extraData.get("user"));

        Group inputGroup = (Group) extraData.get("entity");
        Group outputGroup = entityManager
                .createQuery("select g from Group g where g.name = :group_name",
                        Group.class).setParameter("group_name", inputGroup.getName()).getSingleResult();
        entityManager.remove(outputGroup);
        entityManager.createNativeQuery("DELETE FROM group_user WHERE " +
                "user_id = " + outputUser.getId() + " AND group_id = " + outputGroup.getId()).executeUpdate();
    }

    @Override
    @Transactional
    public void update() throws CustomException {
        auth((User) extraData.get("user"));

        Group inputGroup = (Group) extraData.get("entity");
        Group outputGroup = entityManager
                .createQuery("select g from Group g where g.name = :group_name",
                        Group.class).setParameter("group_name", inputGroup.getName()).getSingleResult();
        outputGroup.setColor(inputGroup.getColor());
    }

    @Override
    @Transactional
    public Collection<Group> list() throws CustomException {
        //check access
        auth((User) extraData.get("user"));
        //get contacts list
        return entityManager.createQuery("from Group", Group.class).getResultList();
    }
}
