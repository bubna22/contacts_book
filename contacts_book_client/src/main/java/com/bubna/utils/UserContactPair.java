package com.bubna.utils;

import com.bubna.model.entities.Contact;
import com.bubna.model.entities.User;

public class UserContactPair extends CustomPair<User, Contact> {

    public UserContactPair(User v1, Contact v2) {
        super(v1, v2);
    }
}
