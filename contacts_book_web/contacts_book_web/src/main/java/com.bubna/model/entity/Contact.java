package com.bubna.model.entity;

import javax.persistence.*;

/**
 * Created by test on 11.07.2017.
 */
@Entity
@Table(name = "contacts")
public class Contact extends EntityAncestor {

    @Id
    @Column(name = "contact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "contact_name")
    private String name;
    @Column(name = "contact_email")
    private String email;
    @Column(name = "contact_num")
    private Integer num;
    @Column(name = "contact_skype")
    private String skype;
    @Column(name = "contact_telegram")
    private String telegram;
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;

    public Contact() {}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getNum() {
        return num;
    }

    public String getSkype() {
        return skype;
    }

    public String getTelegram() {
        return telegram;
    }

    public Group getGroup() {
        return group;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setGroup(Group group) {
        this.group = group;
    }
}
