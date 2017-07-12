package com.bubna.backend;

import com.bubna.entities.Contact;
import com.bubna.entities.Group;
import com.bubna.exceptions.ContactAlreadyExistsException;
import com.bubna.exceptions.GroupAlreadyExistsException;
import com.bubna.exceptions.NoSuchContactException;
import com.bubna.exceptions.NoSuchGroupException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by test on 11.07.2017.
 */
public class CommandController {

    private static CommandController singleton;
    private Scanner scanner;

    public static CommandController getInstance() {
        if (singleton == null) singleton = new CommandController();
        return singleton;
    }

    private CommandController() {
        scanner = new Scanner(System.in);
    }

    private <T> T getCommand() {
        return getCommand(null);
    }

    private <T> T getCommand(Class<T> tClass) {
        Object option = null;
        if (tClass == null) tClass = (Class<T>) String.class;
        while (option == null || option.equals("")) {
            switch (tClass.getName()) {
                case "java.lang.Integer":
                    try {
                        option = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("incorrect input. Try again");
                        scanner.next();
                        continue;
                    }
                    break;
                default:
                    option = scanner.next();
                    if (option.equals("null")) return null;
            }

        }
        return (T) option;
    }

    public void listen() {
        while (true) {
            System.out.println("input command like '[object]/[action]' (for more info input 'contacts_book/help'):");
            String command = getCommand(String.class);
            String[] split = command.split("/");
            if (split.length < 2) {
                System.out.println("incorrect command");
                continue;
            }
            String who = split[0];
            String what = split[1];
            String option = split.length < 3?null:split[2];

            switch (who) {
                case "contact":
                    switch (what) {
                        case "add":
                            Contact c = null;
                            System.out.println("Name: ");
                            option = getCommand();
                            c = new Contact(option, null, -1, null, null, null);
                            try {
                                System.out.println(StorageOverseer.getInstance().contactAdd(c));
                            } catch (ContactAlreadyExistsException e) {
                                System.out.println("contact already exists");
                                continue;
                            }

                            System.out.print("Group: ");
                            while (true) {
                                option = getCommand();
                                if (option != null) {
                                    try {
                                        Group mG = StorageOverseer.getInstance().groupGet(option);
                                    } catch (NoSuchGroupException e) {
                                        System.out.println("no such group; try again (for group = null, type null, when need)");
                                        continue;
                                    }
                                }
                                break;
                            }
                            c.setGroupName(option);

                            System.out.print("Email: ");
                            option = getCommand();
                            c.setEmail(option);

                            System.out.print("Skype: ");
                            option = getCommand();
                            c.setSkype(option);

                            System.out.print("Telegram: ");
                            option = getCommand();
                            c.setTelegram(option);

                            System.out.print("Phone number: ");
                            Integer num = getCommand(Integer.class);
                            c.setNum(num);

                            try {
                                StorageOverseer.getInstance().contactEdit(c);
                            } catch (NoSuchContactException e) {
                                System.out.println("contact was removed");
                                continue;
                            }
                            break;
                        case "rem":
                            if (option == null || option.equals("")) {
                                System.out.println("not find contact name");
                                continue;
                            }
                            c = null;
                            try {
                                c = StorageOverseer.getInstance().contactGet(option);
                                System.out.println(StorageOverseer.getInstance().contactRemove(c));
                            } catch (NoSuchContactException e) {
                                System.out.println("no such contact " + option);
                                continue;
                            }
                            break;
                        case "edit":
                            if (option == null || option.equals("")) {
                                System.out.println("not find contact name");
                                continue;
                            }
                            c = null;
                            try {
                                c = StorageOverseer.getInstance().contactGet(option);
                                System.out.println("Name: " + c.getName());

                                System.out.print("Group: ");
                                while (true) {
                                    option = getCommand();
                                    if (option != null)
                                        try {
                                            Group mG = StorageOverseer.getInstance().groupGet(option);
                                        } catch (NoSuchGroupException e) {
                                            System.out.println("no such group; try again (for group = null, type null, when need)");
                                            continue;
                                        }

                                    break;
                                }
                                c.setGroupName(option);

                                System.out.print("Email: ");
                                option = getCommand();
                                c.setEmail(option);

                                System.out.print("Skype: ");
                                option = getCommand();
                                c.setSkype(option);

                                System.out.print("Telegram: ");
                                option = getCommand();
                                c.setTelegram(option);

                                System.out.print("Phone number: ");
                                num = getCommand(Integer.class);
                                c.setNum(num);

                                System.out.println(StorageOverseer.getInstance().contactEdit(c));
                            } catch (NoSuchContactException e) {
                                System.out.println("no such contact " + option);
                                continue;
                            }
                            break;
                        case "view":
                            if (option == null || option.equals("")) {
                                System.out.println("not find contact name");
                                continue;
                            }
                            c = null;
                            try {
                                c = StorageOverseer.getInstance().contactGet(option);
                                System.out.println("Name: " + c.getName());
                                System.out.println("Group: " + c.getGroupName());
                                System.out.println("Email: " + c.getEmail());
                                System.out.println("Skype: " + c.getSkype());
                                System.out.println("Telegram: " + c.getTelegram());
                                System.out.println("Phone number: " + c.getNum());
                            } catch (NoSuchContactException e) {
                                System.out.println("no such contact " + option);
                                continue;
                            }
                            break;
                        case "list":
                            ArrayList<Contact> contacts = null;
                            if (option == null) {
                                contacts = StorageOverseer.getInstance().contactGetAll();
                                if (contacts.size() > 0) {
                                    for (Contact c1 : contacts) {
                                        System.out.println(contacts.indexOf(c1) + " Name: " + c1.getName() +
                                                " Group: " + c1.getGroupName());
                                    }
                                } else {
                                    System.out.println("no contacts");
                                }
                            } else {
                                Group sGroup = null;
                                try {
                                    sGroup = StorageOverseer.getInstance().groupGet(option);
                                } catch (NoSuchGroupException e) {
                                    System.out.println("no such group " + option);
                                    continue;
                                }
                                contacts = StorageOverseer.getInstance().contactGetAll(sGroup);
                                if (contacts.size() > 0) {
                                    for (Contact c1 : contacts) {
                                        System.out.println(contacts.indexOf(c1) + " Name: " + c1.getName() +
                                                " Group: " + c1.getGroupName());
                                    }
                                } else {
                                    System.out.println("no contacts by group: " + option);
                                }
                            }
                            break;
                        default:
                            System.out.println("incorrect action");
                            continue;
                    }
                    break;
                case "group":
                    switch (what) {
                        case "add":
                            Group g = null;
                            System.out.println("Name: ");
                            option = getCommand();
                            g = new Group(option, -1);
                            try {
                                System.out.println(StorageOverseer.getInstance().groupAdd(g));
                            } catch (GroupAlreadyExistsException e) {
                                System.out.println("group already exists");
                                continue;
                            }

                            System.out.print("Color: ");
                            Integer num = getCommand(Integer.class);
                            g.setColor(num);

                            try {
                                StorageOverseer.getInstance().groupEdit(g);
                            } catch (NoSuchGroupException e) {
                                System.out.println("group was removed");
                                continue;
                            }
                            break;
                        case "rem":
                            if (option == null || option.equals("")) {
                                System.out.println("not find group name");
                                continue;
                            }
                            g = null;
                            try {
                                g = StorageOverseer.getInstance().groupGet(option);
                                System.out.println(StorageOverseer.getInstance().groupRemove(g));
                            } catch (NoSuchGroupException e) {
                                System.out.println("no such group " + option);
                                continue;
                            }
                            break;
                        case "edit":
                            if (option == null || option.equals("")) {
                                System.out.println("not find group name");
                                continue;
                            }
                            g = null;
                            try {
                                g = StorageOverseer.getInstance().groupGet(option);
                                System.out.println("Name: " + g.getName());

                                System.out.print("Phone number: ");
                                num = getCommand(Integer.class);
                                g.setColor(num);

                                System.out.println(StorageOverseer.getInstance().groupEdit(g));
                            } catch (NoSuchGroupException e) {
                                System.out.println("no such group " + option);
                                continue;
                            }
                            break;
                        case "view":
                            if (option == null || option.equals("")) {
                                System.out.println("not find contact name");
                                continue;
                            }
                            g = null;
                            try {
                                g = StorageOverseer.getInstance().groupGet(option);
                                System.out.println("Name: " + g.getName());
                                System.out.println("Color: " + g.getColor());
                            } catch (NoSuchGroupException e) {
                                System.out.println("no such group " + option);
                                continue;
                            }
                            break;
                        case "list":
                            ArrayList<Group> groups = null;
                            groups = StorageOverseer.getInstance().groupGetAll();
                            if (groups.size() > 0) {
                                for (Group g1 : groups) {
                                    System.out.println(groups.indexOf(g1) + " Name: " + g1.getName() +
                                            " Color: " + g1.getColor());
                                }
                            } else {
                                System.out.println("no contacts");
                            }
                            break;
                        default:
                            System.out.println("incorrect action");
                            continue;
                    }
                    break;
                case "contacts_book":
                    switch (what) {
                        case "help":
                            System.out.println("contacts_book powered by bubna");
                            System.out.println("    Entities: contact, group;");
                            System.out.println("    Actions: add, rem(remove) [option], edit [option], view [option], list [option(only for contact)];");
                            System.out.println("    Details:");
                            System.out.println("        Contact: cmd list may have filter - group name;");
                            System.out.println("        Group: cmd list have no filters;");
                            System.out.println("    Example commands:");
                            System.out.println("        contact or group/add");
                            System.out.println("        contact or group/rem/[contact/group name]");
                            System.out.println("        contact or group/edit/[contact/group name]");
                            System.out.println("        contact or group/view/[contact/group name]");
                            System.out.println("        contact/list/[contact/group name]");
                            System.out.println("        contact or group/list");
                            System.out.println("Good luck! :)");
                            break;
                        default:
                            System.out.println("incorrect action");
                    }
                    break;
                default:
                    System.out.println("incorrect entity");
            }
        }
    }
}
