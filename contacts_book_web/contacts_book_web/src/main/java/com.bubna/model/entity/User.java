package com.bubna.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends EntityAncestor {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_login")
    private String login;
    @Column(name = "user_pass")
    private String pass;
    @Column(name = "user_ip")
    private String ip;

    public User() {}

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getIp() {
        return ip;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}

