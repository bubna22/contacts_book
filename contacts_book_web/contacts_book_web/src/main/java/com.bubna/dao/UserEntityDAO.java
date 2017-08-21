package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class UserEntityDAO extends AbstractEntityDAO<User> {

    UserEntityDAO() {
        super();
    }

    @Override
    public void delete() throws CustomException {
        User inputUser = (User) extraData.get("user");
        Criteria criteria = session.createCriteria(User.class);
        User outputUser = (User) criteria
                .add(Restrictions.eq("login", inputUser.getLogin()))
                .uniqueResult();
        outputUser.setIp(null);//Transfer state
    }

    @Override
    public void update() throws CustomException {
        User inputUser = (User) extraData.get("user");
        Criteria criteria = session.createCriteria(User.class);
        User outputUser = (User) criteria
                .add(Restrictions.eq("login", inputUser.getLogin()))
                .add(Restrictions.eq("pass", inputUser.getPass()))
                .uniqueResult();
        outputUser.setIp("1");
    }

}
