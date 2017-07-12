package com.bubna.backend;

import com.bubna.entities.Contact;
import com.bubna.entities.Group;
import com.bubna.exceptions.ContactAlreadyExistsException;
import com.bubna.exceptions.GroupAlreadyExistsException;
import com.bubna.exceptions.NoSuchContactException;
import com.bubna.exceptions.NoSuchGroupException;
import com.bubna.frontend.UIConnectable;

import java.util.ArrayList;

/**
 * Created by test on 11.07.2017.
 */
public class CommandController {

    private static CommandController singleton;

    public static CommandController getInstance() {
        if (singleton == null) singleton = new CommandController();
        return singleton;
    }

    private UIConnectable connectable;

    private CommandController() {
    }

    private String getCommand(String question) {
        String answer = connectable.getAnswer(question);
        if (answer.equals("null")) return null;
        return answer;
    }

    private int getIntCommand(String question) {
        return connectable.getIntAnswer(question);
    }

    private String contactAdd() {
        Contact c = null;
        String option = getCommand("Name: ");
        c = new Contact(option, null, -1, null, null, null);
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(StorageOverseer.getInstance().contactAdd(c));
        } catch (ContactAlreadyExistsException e) {
            return "contact already exists";
        }

        try {
            return sb.append(" ").append(contactEdit(c)).toString();
        } catch (NoSuchContactException e) {
            return "contact was removed";
        }
    }

    private String contactRem(String option) {
        if (option == null || option.equals("")) return "not find contact name";
        Contact c = null;
        try {
            c = StorageOverseer.getInstance().contactGet(option);
            return StorageOverseer.getInstance().contactRemove(c);
        } catch (NoSuchContactException e) {
            return "no such contact " + option;
        }
    }

    private String contactEdit(Contact c) throws NoSuchContactException {
        boolean err = false;
        String option;
        while (true) {
            if (!err) option = getCommand("Group: ");
            else option = getCommand("no such group; try again (for group = null, type null, when need) \n" +
                    "Group: ");

            if (option != null)
                try {
                    Group mG = StorageOverseer.getInstance().groupGet(option);
                } catch (NoSuchGroupException e) {
                    err = true;
                    continue;
                }

            break;
        }
        c.setGroupName(option);
        c.setEmail(getCommand("Email: "));
        c.setSkype(getCommand("Skype: "));
        c.setTelegram(getCommand("Telegram: "));
        c.setNum(getIntCommand("Phone number: "));

        return StorageOverseer.getInstance().contactEdit(c);
    }

    private String contactEdit(String option) {
        if (option == null || option.equals("")) return "not find contact name";
        Contact c = null;
        try {
            c = StorageOverseer.getInstance().contactGet(option);
            return contactEdit(c);
        } catch (NoSuchContactException e) {
            return "no such contact " + option;
        }
    }

    private String contactView(String option) {
        if (option == null || option.equals("")) return "not find contact name";
        Contact c = null;
        try {
            c = StorageOverseer.getInstance().contactGet(option);
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(c.getName()).append("\n");
            sb.append("Group: ").append(c.getGroupName()).append("\n");
            sb.append("Email: ").append(c.getEmail()).append("\n");
            sb.append("Skype: ").append(c.getSkype()).append("\n");
            sb.append("Telegram: ").append(c.getTelegram()).append("\n");
            sb.append("Phone number: ").append(c.getNum());
            return sb.toString();
        } catch (NoSuchContactException e) {
            return "no such contact " + option;
        }
    }

    private String contactList(String option) {
        ArrayList<Contact> contacts = null;
        if (option == null) {
            contacts = StorageOverseer.getInstance().contactGetAll();
            if (contacts.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (Contact c1 : contacts) {
                    sb.append(contacts.indexOf(c1)).append(" Name: ").append(c1.getName()).append(" Group: ")
                            .append(c1.getGroupName()).append("\n");
                }
                return sb.toString();
            } else {
                return "no contacts";
            }
        } else {
            Group sGroup = null;
            try {
                sGroup = StorageOverseer.getInstance().groupGet(option);
            } catch (NoSuchGroupException e) {
                return "no such group " + option;
            }
            contacts = StorageOverseer.getInstance().contactGetAll(sGroup);
            if (contacts.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (Contact c1 : contacts) {
                    sb.append(contacts.indexOf(c1)).append(" Name: ").append(c1.getName()).append(" Group: ")
                            .append(c1.getGroupName()).append("\n");
                }
                return sb.toString();
            } else {
                return "no contacts by group: " + option;
            }
        }
    }

    private String groupAdd() {
        Group g = null;
        String option = getCommand("Name: ");
        g = new Group(option, -1);
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(StorageOverseer.getInstance().groupAdd(g));
        } catch (GroupAlreadyExistsException e) {
            return "group already exists";
        }

        g.setColor(getIntCommand("Color: "));

        try {
            return sb.append(StorageOverseer.getInstance().groupEdit(g)).toString();
        } catch (NoSuchGroupException e) {
            return "group was removed";
        }
    }

    private String groupRem(String option) {
        if (option == null || option.equals("")) return "not find group name";
        Group g = null;
        try {
            g = StorageOverseer.getInstance().groupGet(option);
            return StorageOverseer.getInstance().groupRemove(g);
        } catch (NoSuchGroupException e) {
            return "no such group " + option;
        }
    }

    private String groupEdit(String option) {
        if (option == null || option.equals("")) return "not find group name";
        Group g = null;
        try {
            g = StorageOverseer.getInstance().groupGet(option);
            g.setColor(getIntCommand("Color: "));

            return StorageOverseer.getInstance().groupEdit(g);
        } catch (NoSuchGroupException e) {
            return "no such group " + option;
        }
    }

    private String groupView(String option) {
        if (option == null || option.equals("")) return "not find contact name";
        Group g = null;
        try {
            g = StorageOverseer.getInstance().groupGet(option);
            return "Name: " + g.getName() + "Color: " + g.getColor();
        } catch (NoSuchGroupException e) {
            return "no such group " + option;
        }
    }

    private String groupList() {
        ArrayList<Group> groups = null;
        groups = StorageOverseer.getInstance().groupGetAll();
        if (groups.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Group g1 : groups) {
                sb.append(groups.indexOf(g1)).append(" Name: ").append(g1.getName()).append(" Color: ")
                        .append(g1.getColor()).append("\n");
            }
            return sb.toString();
        } else {
            return "no groups";
        }
    }

    private String help() {
        return "contacts_book powered by bubna\n" +
                "    Entities: contact, group;\n" +
                "    Actions: add, rem(remove) [option], edit [option], view [option], list [option(only for contact)];\n" +
                "    Details:\n" +
                "        Contact: cmd list may have filter - group name;\n" +
                "        Group: cmd list have no filters;\n" +
                "    Example commands:\n" +
                "        contact or group/add\n" +
                "        contact or group/rem/[contact/group name]\n" +
                "        contact or group/edit/[contact/group name]\n" +
                "        contact or group/view/[contact/group name]\n" +
                "        contact/list/[contact/group name]\n" +
                "        contact or group/list\n" +
                "Good luck! :)";
    }

    public String listen(UIConnectable connectable) {
        this.connectable = connectable;
        String command = getCommand("");
        String[] split = command.split("/");
        if (split.length < 2) {
            return "incorrect command";
        }
        String who = split[0];
        String what = split[1];
        String option = split.length < 3?null:split[2];

        switch (who) {
            case "contact":
                switch (what) {
                    case "add":
                        return contactAdd();
                    case "rem":
                        return contactRem(option);
                    case "edit":
                        return contactEdit(option);
                    case "view":
                        return contactView(option);
                    case "list":
                        return contactList(option);
                    default:
                        return "incorrect action";
                }
            case "group":
                switch (what) {
                    case "add":
                        return groupAdd();
                    case "rem":
                        return groupRem(option);
                    case "edit":
                        return groupEdit(option);
                    case "view":
                        return groupView(option);
                    case "list":
                        return groupList();
                    default:
                        return "incorrect action";
                }
            case "contacts_book":
                switch (what) {
                    case "help":
                        return help();
                    default:
                        return "incorrect action";
                }
            default:
                return "incorrect entity";
        }
    }
}
