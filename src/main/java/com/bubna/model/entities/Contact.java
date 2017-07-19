package com.bubna.model.entities;

/**
 * Created by test on 11.07.2017.
 */
public class Contact extends EntityAncestor {

    private String email;
    private Integer num;
    private String skype;
    private String telegram;
    private String groupName;

    public Contact(String name, String email, Integer num, String skype, String telegram, String groupName) {
        this.name = name;
        this.email = email;
        this.num = num;
        this.skype = skype;
        this.telegram = telegram;
        this.groupName = groupName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("__").append(this.getClass().getName()).append("\n")
                .append("____").append("name: ").append(name).append("\n")
                .append("____").append("email: ").append(email).append("\n")
                .append("____").append("num: ").append(num).append("\n")
                .append("____").append("skype: ").append(skype).append("\n")
                .append("____").append("telegram: ").append(telegram).append("\n")
                .append("____").append("groupName: ").append(groupName).append("\n");
        return sb.toString();
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
