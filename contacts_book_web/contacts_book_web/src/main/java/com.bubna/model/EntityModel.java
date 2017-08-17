package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.dao.cmd.CreateCommand;
import com.bubna.dao.cmd.ListCommand;
import com.bubna.dao.cmd.ModifyCommand;
import com.bubna.dao.cmd.RemoveCommand;

public class EntityModel extends AbstractModel {

    public EntityModel(ObservablePart observable, DAO dao) {
        super(observable, dao);
        cmds.put("list", new ListCommand("list"));
        cmds.put("create", new CreateCommand("create"));
        cmds.put("delete", new RemoveCommand("delete"));
        cmds.put("modify", new ModifyCommand("modify"));
    }

}
