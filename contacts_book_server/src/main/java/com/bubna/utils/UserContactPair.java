package com.bubna.utils;

import com.bubna.model.entity.Contact;
import com.bubna.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserContactPair extends CustomPair<User, Contact> {

    public UserContactPair(User v1, Contact v2) {
        super(v1, v2);
    }
}
