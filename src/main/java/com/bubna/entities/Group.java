package com.bubna.entities;

/**
 * Created by test on 11.07.2017.
 */
public class Group extends EntityAncestor {

    private String name;
    private int color;

    public Group(String name, int Color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
