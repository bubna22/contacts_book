package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

public class UserEntityDAO extends AbstractEntityDAO<User> {

    UserEntityDAO() {
        super();
    }

    @Override
    @Transactional
    public void delete() throws CustomException {
        User inputUser = (User) extraData.get("user");

        User outputUser = entityManager
                .createQuery("select u from User u where u.login = :user_login",
                        User.class).setParameter("user_login", inputUser.getLogin()).getSingleResult();
        outputUser.setIp(null);
    }

    @Override
    @Transactional
    public User get() {
        User inputUser = (User) extraData.get("user");
        return entityManager
                .createQuery("select u from User u where u.login = :user_login",
                        User.class).setParameter("user_login", inputUser.getLogin()).getSingleResult();
    }

    @Override
    @Transactional
    public void update() throws CustomException {
        User inputUser = (User) extraData.get("user");
        User outputUser = entityManager
                .createQuery("select u from User u where u.login = :user_login and u.pass = :user_pass",
                        User.class).setParameter("user_login", inputUser.getLogin())
                .setParameter("user_pass", inputUser.getPass()).getSingleResult();
        outputUser.setIp("1");
    }

}
