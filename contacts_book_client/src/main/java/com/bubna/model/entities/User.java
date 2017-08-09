package com.bubna.model.entities;

public class User extends EntityAncestor {

    private String pass;
    private String ip;

    public User(String login, String pass, String ip) {
        this.name = login;
        this.pass = pass;
        this.ip = ip;
    }

    public String getPass() {
        return pass;
    }

    public String getIp() {
        return ip;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}

