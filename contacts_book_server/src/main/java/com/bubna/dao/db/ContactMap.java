package com.bubna.dao.db;

import com.bubna.model.entity.Contact;
import org.postgresql.util.PGobject;

class ContactMap extends CustomMap<Contact> {

    String contact_name;
    String contact_email;
    String contact_telegram;
    Integer contact_num;
    String contact_skype;
    String group_name;

    ContactMap(PGobject data) {
        super(data);
    }

    ContactMap(Contact contact) {
        super(contact);
        this.contact_name = contact.getName();
        this.contact_email = contact.getEmail();
        this.contact_num = contact.getNum();
        this.contact_skype = contact.getSkype();
        this.contact_telegram = contact.getTelegram();
        this.group_name = contact.getGroupName();
        prepareOutput();
    }

    @Override
    protected void prepareInput(String[] values) {
        contact_name = values[0].replace("'", "");
        contact_email = values[1].replace("'", "");
        contact_telegram = values[2].replace("'", "");
        contact_num = values[3].contains("'")||values[3].equals("")?0:new Integer(values[3]);
        contact_skype = values[4].replace("'", "");
        group_name = values[5].replace("'", "");
    }

    @Override
    protected void prepareOutput() {
        type = "contact_type";
        value = "(" + contact_name + "," + contact_email + "," + contact_telegram + "," +
                contact_num + "," + contact_skype + "," + group_name + ")";
    }

    @Override
    protected Contact getEntity() {
        return new Contact(
                this.contact_name,
                this.contact_email,
                this.contact_num,
                this.contact_skype,
                this.contact_telegram,
                this.group_name);
    }

}
