package com.bubna.view;

import com.bubna.model.entities.User;
import javafx.application.Platform;

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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webEngine.executeScript("loggedIn('" + entity.getName() + "', '" + entity.getPass() + "', '" + entity.getIp() + "');");
            }
        });
    }

    @Override
    protected boolean checkType(Object obj) {
        return (obj instanceof User) || (obj instanceof Exception);
    }
}
