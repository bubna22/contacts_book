package com.bubna.model.entities;

public class User {

    private Integer color;

    public Group(String name, Integer color) {
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

