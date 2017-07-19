package com.bubna.model.entities;

/**
 * Created by test on 11.07.2017.
 */
public class Group extends EntityAncestor {

    private Integer color;

    public Group(String name, Integer Color) {
        this.name = name;
        this.color = color;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("__").append(this.getClass().getName()).append("\n")
                .append("____").append("name: ").append(name).append("\n")
                .append("____").append("color: ").append(color).append("\n");
        return sb.toString();
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
