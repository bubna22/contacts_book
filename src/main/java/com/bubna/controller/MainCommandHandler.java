package com.bubna.controller;

import com.bubna.exceptions.IncorrectInputException;
import com.bubna.model.StorageModel;

import java.util.HashMap;

class MainCommandHandler implements CommandHandler {

    private CommandHandler next;

    MainCommandHandler() {}

    @Override
    public void setNext(CommandHandler next) {
        this.next = next;
    }

    @Override
    public void handle(String cmd) {
        String[] split = cmd.split(" ");

        if (split.length < 2) {
            StorageModel.INSTANCE.applyException(new IncorrectInputException());
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
            StorageModel.INSTANCE.applyException(e1);
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
                    StorageModel.INSTANCE.applyException(e1);
                    return;
                }
                try {
                    inputVariables.put(v, v.parseVariable(split[i + 1]));
                } catch (IncorrectInputException e1) {
                    StorageModel.INSTANCE.applyException(e1);
                    return;
                }
            }
            Object[] varsWithState = entity.availableActions.get(action);
            for (int i = 0; i < varsWithState.length; i++) {
                Object[] varWithState = (Object[]) varsWithState[i];
                if (!((boolean) varWithState[1])) continue;
                if (!inputVariables.containsKey((CommandController.Action.Variable) varWithState[0])) {
                    StorageModel.INSTANCE.applyException(new IncorrectInputException("\nvariable " +
                            ((CommandController.Action.Variable) varWithState[0]).getShortName() + " not exists"));
                    return;
                }
            }
        } else if (split.length > 2) {
            StorageModel.INSTANCE.applyException(new IncorrectInputException());
            return;
        }
        StorageModel.INSTANCE.apply(entity, action, inputVariables);
        if (next == null) return;
        next.handle(cmd);
    }
}
