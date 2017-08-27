package com.bubna.service;

import com.bubna.dao.cmd.Command;
import com.bubna.model.Model;
import com.bubna.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public User getUser(String login) {
        User user = new User();
        user.setLogin(login);
        user.setPass("test");

//        Model userModel = (Model) applicationContext.getBean("userModel");
//        Command cmd = userModel.getCommand("get");
//        cmd.addInput("user", user);
//        cmd.execute();
        return user;
    }
}