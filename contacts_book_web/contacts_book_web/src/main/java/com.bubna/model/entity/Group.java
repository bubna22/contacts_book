package com.bubna.model.entity;

import javax.persistence.*;

/**
 * Created by test on 11.07.2017.
 */
@Entity
@Table(name = "groups")
public class Group extends EntityAncestor {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "group_name")
    private String name;
    @Column(name = "group_color")
    private Integer color;

    public Group() {}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getColor() {
        return color;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
