package com.bubna.controller;

import com.bubna.model.StorageModel;

class HelpCoRCommandHandler implements CoRCommandHandler {

    private CoRCommandHandler next;

    HelpCoRCommandHandler() {}

    @Override
    public void setNext(CoRCommandHandler next) {
        this.next = next;
    }

    @Override
    public void handle(String cmd) {
        if (cmd.equals("help")) {
            StringBuilder sb = new StringBuilder();
            sb.append("--->--->example(space is separator): [Entity] [Action] [[var name] [var value]...]\n");

            for (int i = 0; i < CommandController.Entity.values().length; i++) {
                CommandController.Entity e = CommandController.Entity.values()[i];
                sb.append("--->").append(e.name().toLowerCase()).append("<---").append("\n");
                CommandController.Action[] keys = new CommandController.Action[e.availableActions.size()];
                e.availableActions.keySet().toArray(keys);
                for (int j = 0; j < keys.length; j++) {
                    CommandController.Action a = keys[j];
                    sb.append("--->--->").append(a.help()).append("\n");
                    Object[] variablesWithState = e.availableActions.get(a);
                    for (int k = 0; k < variablesWithState.length; k++) {
                        Object[] variableWithState = ((Object[]) variablesWithState[k]);
                        sb.append("--->--->--->").append(((CommandController.Action.Variable)variableWithState[0]).help()).append("\n");
                    }
                }
            }

            StorageModel.INSTANCE.applyString(sb.toString());
            return;
        }
        if (next == null) return;
        next.handle(cmd);
    }
}
