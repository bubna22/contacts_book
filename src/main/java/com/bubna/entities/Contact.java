package com.bubna.entities;

/**
 * Created by test on 11.07.2017.
 */
public class Contact extends EntityAncestor {

    private String name;
    private String email;
    private int num;
    private String skype;
    private String telegram;
    private String groupName;

    public Contact(String name, String email, int num, String skype, String telegram, String groupName) {
        this.name = name;
        this.email = email;
        this.num = num;
        this.skype = skype;
        this.telegram = telegram;
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getNum() {
        return num;
    }

    public String getSkype() {
        return skype;
    }

    public String getTelegram() {
        return telegram;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
