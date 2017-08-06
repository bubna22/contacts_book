package com.bubna.view;

import com.bubna.model.entities.User;

public class UserView extends AbstractView<User> {

    UserView(String jsName) {
        super(jsName);
    }

    @Override
    protected User fromHtml(String html) {
        String[] splitted = html.split("/");
        return new User(splitted[0], splitted[1], "1");
    }

    @Override
    protected void parseEntity(User entity) {
        webEngine.executeScript("loggedIn();");
    }

    @Override
    protected boolean checkType(Object obj) {
        return (obj instanceof User) || (obj instanceof Exception);
    }
}
