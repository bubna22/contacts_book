package com.bubna.model;

import com.bubna.dao.EntityDAO;
import com.bubna.dao.cmd.CreateEntityCommand;
import com.bubna.dao.cmd.ListEntityCommand;
import com.bubna.dao.cmd.ModifyEntityCommand;
import com.bubna.dao.cmd.RemoveEntityCommand;

public class EntityModel extends AbstractModel {

    public EntityModel(ObservablePart observable, EntityDAO entityDao) {
        super(observable, entityDao);
        cmds.put("list", new ListEntityCommand("list"));
        cmds.put("create", new CreateEntityCommand("create"));
        cmds.put("delete", new RemoveEntityCommand("delete"));
        cmds.put("modify", new ModifyEntityCommand("modify"));
    }

}
