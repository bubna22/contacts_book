package com.bubna.dao.db;

import com.bubna.model.entity.Group;
import org.postgresql.util.PGobject;

class GroupMap extends CustomMap<Group> {

    Integer group_color;
    String group_name;

    GroupMap(PGobject data) {
        super(data);
    }

    GroupMap(Group group) {
        super(group);
        this.group_name = group.getName();
        this.group_color = group.getColor();
        prepareOutput();
    }

    @Override
    protected void prepareInput(String[] values) {
        group_name = values[0].replace("'", "");
        group_color = values.length<2||values[1].contains("'")||values[1].equals("")?0:new Integer(values[1]);
    }

    @Override
    protected void prepareOutput() {
        type = "group_type";
        value = "(" + group_name + "," + group_color + ")";
    }

    @Override
    protected Group getEntity() {
        return new Group(
                this.group_name,
                this.group_color);
    }
}