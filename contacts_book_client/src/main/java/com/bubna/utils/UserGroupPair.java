package com.bubna.utils;

import com.bubna.model.entities.Group;
import com.bubna.model.entities.User;

public class UserGroupPair extends CustomPair<User, Group> {

    public UserGroupPair(User v1, Group v2) {
        super(v1, v2);
    }
}
