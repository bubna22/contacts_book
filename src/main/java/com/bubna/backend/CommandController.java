package com.bubna.backend;

import com.bubna.entities.Contact;
import com.bubna.entities.Group;
import com.bubna.exceptions.*;
import com.bubna.frontend.UIConnectable;

import java.util.ArrayList;

/**
 * Created by test on 11.07.2017.
 */
public enum CommandController {

    INSTANCE;

    private enum Action {
        ADD("add new entity", null, null),
        REM("remove entity", null, "/[entity_name]"),
        EDIT("edit entity", null, "/[entity_name]"),
        VIEW("view entity", null, "/[entity_name]"),
        LIST("view all entities", null, null),
        LIST_WITH_PARAMS("view all entities (maybe has params)", "list", "/[filter_name]"),
        HELP("display all commands", null, null);

        private String text;
        private String simpleName;
        private String paramBlock;

        Action(String text, String simpleName, String paramBlock) {
            this.text = text;
            if (simpleName == null) this.simpleName = this.name();
            else this.simpleName = simpleName;
            this.paramBlock = paramBlock;
        }
    }

    private enum Entity {

        CONTACTS_BOOK(new Action[]{Action.HELP}) {
            protected String help() {
                StringBuilder sb = new StringBuilder().append("contacts_book powered by bubna\n");
                for (int i = 0; i < Entity.values().length; i++) {
                    Entity e = Entity.values()[i];
                    sb.append(" -").append(e.name().toLowerCase()).append("\n");
                    for (int j = 0; j < e.availableActions.length; j++) {
                        Action a = e.availableActions[j];
                        sb.append("     --").append(a.simpleName.toLowerCase()).append(" - ").append(a.text).append("\n");
                        sb.append("     example: ").append(e.name().toLowerCase()).append("/").append(a.simpleName.toLowerCase());
                        if (a.paramBlock != null) sb.append(a.paramBlock);
                        sb.append("\n");
                    }
                }
                return sb.toString();
            }

            protected String defaultAction() {
                return help();
            }
        },
        CONTACT(new Action[]{Action.ADD, Action.REM, Action.EDIT, Action.VIEW, Action.LIST_WITH_PARAMS}) {
            protected String add() throws InitException {
                Contact c = null;
                String option = CommandController.INSTANCE.getCommand("Name: ");
                c = new Contact(option, null, -1, null, null, null);
                StringBuilder sb = new StringBuilder();
                try {
                    sb.append(StorageOverseer.getInstance().contactAdd(c));
                } catch (ContactAlreadyExistsException e) {
                    return "contact already exists";
                }

                try {
                    return sb.append(" ").append(edit(c)).toString();
                } catch (NoSuchContactException e) {
                    return "contact was removed";
                }
            }

            protected String rem(String option) throws InitException {
                if (option == null || option.equals("")) return "not find contact name";
                Contact c = null;
                try {
                    c = StorageOverseer.getInstance().contactGet(option);
                    return StorageOverseer.getInstance().contactRemove(c);
                } catch (NoSuchContactException e) {
                    return "no such contact " + option;
                }
            }


            String edit(Contact c) throws NoSuchContactException, InitException {
                boolean err = false;
                String option;
                while (true) {
                    if (!err) option = CommandController.INSTANCE.getCommand("Group: ");
                    else option = CommandController.INSTANCE.getCommand("no such group; try again \n" +
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
                c.setEmail(CommandController.INSTANCE.getCommand("Email: "));
                c.setSkype(CommandController.INSTANCE.getCommand("Skype: "));
                c.setTelegram(CommandController.INSTANCE.getCommand("Telegram: "));
                c.setNum(CommandController.INSTANCE.getIntCommand("Phone number: "));

                return StorageOverseer.getInstance().contactEdit(c);
            }

            protected String edit(String option) throws InitException {
                if (option == null || option.equals("")) return "not find contact name";
                Contact c = null;
                try {
                    c = StorageOverseer.getInstance().contactGet(option);
                    return edit(c);
                } catch (NoSuchContactException e) {
                    return "no such contact " + option;
                }
            }

            protected String view(String option) throws InitException {
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

            protected String list(String option) throws InitException {
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
        },
        GROUP(new Action[]{Action.ADD, Action.REM, Action.EDIT, Action.VIEW, Action.LIST}) {
            protected String add() throws InitException {
                Group g = null;
                String option = CommandController.INSTANCE.getCommand("Name: ");
                g = new Group(option, -1);
                StringBuilder sb = new StringBuilder();
                try {
                    sb.append(StorageOverseer.getInstance().groupAdd(g));
                } catch (GroupAlreadyExistsException e) {
                    return "group already exists";
                }

                g.setColor(CommandController.INSTANCE.getIntCommand("Color: "));

                try {
                    return sb.append(StorageOverseer.getInstance().groupEdit(g)).toString();
                } catch (NoSuchGroupException e) {
                    return "group was removed";
                }
            }

            protected String rem(String option) throws InitException {
                if (option == null || option.equals("")) return "not find group name";
                Group g = null;
                try {
                    g = StorageOverseer.getInstance().groupGet(option);
                    return StorageOverseer.getInstance().groupRemove(g);
                } catch (NoSuchGroupException e) {
                    return "no such group " + option;
                }
            }

            protected String edit(String option) throws InitException {
                if (option == null || option.equals("")) return "not find group name";
                Group g = null;
                try {
                    g = StorageOverseer.getInstance().groupGet(option);
                    g.setColor(CommandController.INSTANCE.getIntCommand("Color: "));

                    return StorageOverseer.getInstance().groupEdit(g);
                } catch (NoSuchGroupException e) {
                    return "no such group " + option;
                }
            }

            protected String view(String option) throws InitException {
                if (option == null || option.equals("")) return "not find contact name";
                Group g = null;
                try {
                    g = StorageOverseer.getInstance().groupGet(option);
                    return "Name: " + g.getName() + "Color: " + g.getColor();
                } catch (NoSuchGroupException e) {
                    return "no such group " + option;
                }
            }

            protected String list(String option) throws InitException {
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
        };

        private Action[] availableActions = {Action.HELP};

        Entity(Action[] actions) {
            availableActions = actions;
        }

        protected String add() throws InitException {
            return defaultAction();
        }
        protected String rem(String option) throws InitException {
            return defaultAction();
        }
        protected String edit(String option) throws InitException {
            return defaultAction();
        }
        protected String view(String option) throws InitException {
            return defaultAction();
        }
        protected String list(String option) throws InitException {
            return defaultAction();
        }
        protected String help() {
            return defaultAction();
        }
        protected String defaultAction() {
            return "incorrect action";
        }
    }

    private UIConnectable connectable;

    private String getCommand(String question) {
        String answer = connectable.getAnswer(question);
        if (answer.equals("")) return null;
        return answer;
    }

    private int getIntCommand(String question) {
        return connectable.getIntAnswer(question);
    }

    public String listen(UIConnectable connectable) throws InitException {
        this.connectable = connectable;
        String command = getCommand("");
        if (command == null) return "no command";
        String[] split = command.split("/");
        if (split.length < 2) {
            return "incorrect command";
        }
        String who = split[0].toUpperCase();
        String what = split[1].toUpperCase();
        String option = split.length < 3 ? null : split[2];

        try {
            switch (Action.valueOf(what)) {
                case ADD:
                    return Entity.valueOf(who).add();
                case REM:
                    return Entity.valueOf(who).rem(option);
                case EDIT:
                    return Entity.valueOf(who).edit(option);
                case VIEW:
                    return Entity.valueOf(who).view(option);
                case LIST:
                    return Entity.valueOf(who).list(option);
                case HELP:
                    return Entity.valueOf(who).help();
                default:
                    return Entity.valueOf(who).defaultAction();
            }
        } catch (IllegalArgumentException e) {
            return "incorrect entity";
        }
    }

}
