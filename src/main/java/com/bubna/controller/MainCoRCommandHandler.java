package com.bubna.controller;

import com.bubna.exceptions.IncorrectInputException;
import com.bubna.model.StorageModel;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.utils.CustomPair;

import java.util.HashMap;

class MainCoRCommandHandler implements CoRCommandHandler {

    private CoRCommandHandler next;

    MainCoRCommandHandler() {}

    @Override
    public void setNext(CoRCommandHandler next) {
        this.next = next;
    }

    @Override
    public void handle(String cmd) {
        String[] split = cmd.split(" ");

        if (split.length < 2) {
            StorageModel.getInstance().applyException(new IncorrectInputException());
            return;
        }
        String who = split[0].toUpperCase();
        String what = split[1].toUpperCase();

        CommandController.Entity entity = null;
        CommandController.Action action = null;

        try {
            entity = CommandController.Entity.valueOf(who);
            action = CommandController.Action.valueOf(what);
        } catch (IllegalArgumentException e1) {
            StorageModel.getInstance().applyException(e1);
            return;
        }

        HashMap<CommandController.Action.Variable, Object> inputVariables = new HashMap<>();

        if (CommandController.Entity.needVarsForAction(entity, action)) {
            for (int i = 2; i < split.length; i += 2) {
                if (i%2 != 0) continue;
                if (i == split.length - 1) break;
                CommandController.Action.Variable v = null;
                try {
                    v = CommandController.Action.Variable.getByShortName(split[i]);
                } catch (IncorrectInputException e1) {
                    StorageModel.getInstance().applyException(e1);
                    return;
                }
                try {
                    inputVariables.put(v, v.parseVariable(split[i + 1]));
                } catch (IncorrectInputException e1) {
                    StorageModel.getInstance().applyException(e1);
                    return;
                }
            }
            CustomPair<CommandController.Action.Variable, Boolean>[] varsWithState = entity.availableActions.get(action);
            for (int i = 0; i < varsWithState.length; i++) {
                CustomPair<CommandController.Action.Variable, Boolean> varWithState = varsWithState[i];
                if (!varWithState.getV2()) continue;
                if (!inputVariables.containsKey(varWithState.getV1())) {
                    StorageModel.getInstance().applyException(new IncorrectInputException("\nvariable " +
                            varWithState.getV1().getShortName() + " not exists"));
                    return;
                }
            }
        } else if (split.length > 2) {
            StorageModel.getInstance().applyException(new IncorrectInputException());
            return;
        }

        EntityAncestor entityAncestor = null;

        switch (entity) {
            case CONTACT:
                entityAncestor = new Contact(
                        inputVariables.containsKey(CommandController.Action.Variable.CONTACT_NAME)?
                                (String) inputVariables.get(CommandController.Action.Variable.CONTACT_NAME):
                                null,
                        inputVariables.containsKey(CommandController.Action.Variable.CONTACT_EMAIL)?
                                (String) inputVariables.get(CommandController.Action.Variable.CONTACT_EMAIL):
                                null,
                        inputVariables.containsKey(CommandController.Action.Variable.CONTACT_NUM)?
                                (Integer) inputVariables.get(CommandController.Action.Variable.CONTACT_NUM):
                                null,
                        inputVariables.containsKey(CommandController.Action.Variable.CONTACT_SKYPE)?
                                (String) inputVariables.get(CommandController.Action.Variable.CONTACT_SKYPE):
                                null,
                        inputVariables.containsKey(CommandController.Action.Variable.CONTACT_TELEGRAM)?
                                (String) inputVariables.get(CommandController.Action.Variable.CONTACT_TELEGRAM):
                                null,
                        inputVariables.containsKey(CommandController.Action.Variable.GROUP_NAME)?
                                (String) inputVariables.get(CommandController.Action.Variable.GROUP_NAME):
                                null
                );
                break;
            case GROUP:
                entityAncestor = new Group(
                        inputVariables.containsKey(CommandController.Action.Variable.GROUP_NAME)?
                                (String) inputVariables.get(CommandController.Action.Variable.GROUP_NAME):
                                null,
                        inputVariables.containsKey(CommandController.Action.Variable.GROUP_COLOR)?
                                (Integer) inputVariables.get(CommandController.Action.Variable.GROUP_COLOR):
                                null
                );
                break;
        }
        switch (action) {
            case VIEW:
                StorageModel.getInstance().view(entityAncestor);
                break;
            case LIST:
                StorageModel.getInstance().list(entityAncestor);
                break;
            case MODIFY:
                StorageModel.getInstance().modify(entityAncestor);
                break;
        }
        if (next == null) return;
        next.handle(cmd);
    }
}
