package com.bubna.service.socket_io;

import com.bubna.exceptions.InitException;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.model.entities.User;
import com.bubna.utils.TransferObject;
import com.bubna.utils.UserGroupPair;
import com.bubna.utils.Utils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final class SIOGroupService extends SIOEntityAncestorService {


    @Override
    protected String getEntityName() {
        return "group";
    }

    @Override
    protected HashMap<String, EntityAncestor> getFromString(String data) {
        HashMap<String, EntityAncestor> dataRetruned = new HashMap<>();

        ArrayList<Group> groups = Utils.INSTANCE.getGson().fromJson(data, new TypeToken<List<Group>>(){}.getType());
        Utils.INSTANCE.putGson();

        for (int i = 0; i < groups.size(); i++) {
            Group g = groups.get(i);
            dataRetruned.put(g.getName(), g);
        }

        return dataRetruned;
    }

    @Override
    protected TransferObject getModifyTransferObject(User user, EntityAncestor entityAncestor) throws InitException {
        try {
            UserGroupPair userGroupPair = new UserGroupPair(user, (Group) entityAncestor);
            return new TransferObject("contact", "modify", Utils.INSTANCE.getGson().toJson(userGroupPair));
        } finally {
            Utils.INSTANCE.putGson();
        }
    }
}
