package com.bubna.model.entities;

/**
 * Created by test on 11.07.2017.
 */
public class Group extends EntityAncestor {

    private Integer color;

    public Group(String name, Integer color) {
        this.name = name;
        this.color = color;
    }

    public Integer getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
