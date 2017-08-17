package com.bubna.service.socket_io;

import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;
import com.bubna.utils.TransferObject;
import com.bubna.utils.UserContactPair;
import com.bubna.utils.Utils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final class SIOContactService extends SIOEntityAncestorService {

    @Override
    protected String getEntityName() {
        return "contact";
    }

    @Override
    protected HashMap<String, EntityAncestor> getFromString(String data) {
        HashMap<String, EntityAncestor> dataRetruned = new HashMap<>();
        ArrayList<Contact> contacts = Utils.INSTANCE.getGson().fromJson(data, new TypeToken<List<Contact>>(){}.getType());
        Utils.INSTANCE.putGson();

        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            dataRetruned.put(c.getName(), c);
        }

        return dataRetruned;
    }

    @Override
    protected TransferObject getModifyTransferObject(User user, EntityAncestor entityAncestor) throws InitException {
        try {
            UserContactPair userContactPair = new UserContactPair(user, (Contact) entityAncestor);
            return new TransferObject("contact", "modify", Utils.INSTANCE.getGson().toJson(userContactPair));
        } finally {
            Utils.INSTANCE.putGson();
        }
    }
}
