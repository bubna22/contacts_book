package com.bubna.backend;

import com.bubna.exceptions.IncorrectInputException;

import java.util.HashMap;

/**
 * Created by test on 11.07.2017.
 */
public enum CommandController {

    INSTANCE;

    public enum Action {
        ADD("add entity"),
        REM("remove entity"),
        EDIT("add entity"),
        VIEW("get properties of entity"),
        LIST("list entities");

        private String help;

        enum Variable {
            NONE(null, "no available args", null) {
                protected Object parseVariable(Object o) {
                    throw new IllegalArgumentException();
                }
                protected String help() {
                    return help;
                }
            },
            CONTACT_NAME("cname", "contact name", String.class),
            CONTACT_EMAIL("cemail", "contact email", String.class),
            CONTACT_NUM("cnum", "contact phone number", Number.class) {
                protected Object parseVariable(Object o) throws IncorrectInputException {
                    if (o.getClass().equals(String.class)) {
                        try {
                            return Integer.parseInt((String) o);
                        } catch (NumberFormatException e) {
                            throw new IncorrectInputException("\n" + o.toString() +
                                    " not a number; var name: " + getShortName());
                        }
                    }
                    throw new IncorrectInputException();
                }
            },
            CONTACT_SKYPE("cskype", "contact skype", String.class),
            CONTACT_TELEGRAM("ctelegram", "contact telegram", String.class),
            GROUP_NAME("gname", "group name", String.class),
            GROUP_COLOR("gcolor", "group color", Number.class) {
                protected Object parseVariable(Object o) throws IncorrectInputException {
                    if (o.getClass().equals(String.class)) {
                        try {
                            return Integer.parseInt((String) o);
                        } catch (NumberFormatException e) {
                            throw new IncorrectInputException("\n" + o.toString() +
                                    " not a number; var name: " + getShortName());
                        }
                    }
                    throw new IncorrectInputException();
                }
            };

            private String shortName;
            protected Class<?> availInput;
            protected String help;

            protected String getShortName() {
                return shortName;
            }

            protected String help() {
                return "Variable name: " + shortName + "; Description: " + help;
            }

            protected Object parseVariable(Object o) throws IncorrectInputException {
                if (o.getClass().equals(availInput)) return o;
                throw new IllegalArgumentException();
            }

            private static Variable getByShortName(String shortName) throws IncorrectInputException {
                if (shortName.equals(NONE.getShortName())) throw new IllegalArgumentException("\nno vars available");
                for (int i = 0; i < Variable.values().length; i++) {
                    Variable v = Variable.values()[i];
                    if (shortName.equals(v.getShortName())) return v;
                }
                throw new IncorrectInputException("\nno such variable");
            }

            Variable(String shortName, String helpStr, Class<?> availInput) {
                this.shortName = shortName;
                this.help = helpStr;
                this.availInput = availInput;
            }
        }

        Action(String helpStr) {
            this.help = helpStr;
        }

        private String help() {
            return name().toLowerCase() + " - " + help;
        }

    }

    public enum Entity {

        CONTACT() {
            protected void init() {
                availableActions = new HashMap<>();
                availableActions.put(Action.ADD, new Object[] {
                        new Object[]{Action.Variable.CONTACT_NAME, Boolean.TRUE},
                        new Object[]{Action.Variable.CONTACT_EMAIL, Boolean.FALSE},
                        new Object[]{Action.Variable.CONTACT_NUM, Boolean.FALSE},
                        new Object[]{Action.Variable.CONTACT_SKYPE, Boolean.FALSE},
                        new Object[]{Action.Variable.CONTACT_TELEGRAM, Boolean.FALSE},
                        new Object[]{Action.Variable.GROUP_NAME, Boolean.FALSE}
                });
                availableActions.put(Action.REM, new Object[]{new Object[]{Action.Variable.CONTACT_NAME}});
                availableActions.put(Action.EDIT, new Object[] {
                        new Object[]{Action.Variable.CONTACT_NAME, Boolean.TRUE},
                        new Object[]{Action.Variable.CONTACT_EMAIL, Boolean.FALSE},
                        new Object[]{Action.Variable.CONTACT_NUM, Boolean.FALSE},
                        new Object[]{Action.Variable.CONTACT_SKYPE, Boolean.FALSE},
                        new Object[]{Action.Variable.CONTACT_TELEGRAM, Boolean.FALSE},
                        new Object[]{Action.Variable.GROUP_NAME, Boolean.FALSE}
                });
                availableActions.put(Action.VIEW, new Object[] {new Object[] {Action.Variable.CONTACT_NAME, Boolean.TRUE}});
                availableActions.put(Action.LIST, new Object[] {new Object[] {Action.Variable.GROUP_NAME, Boolean.FALSE}});
            }
        },
        GROUP() {
            protected void init() {
                availableActions = new HashMap<>();
                availableActions.put(Action.ADD, new Object[]{
                        new Object[]{Action.Variable.GROUP_NAME, Boolean.TRUE},
                        new Object[]{Action.Variable.GROUP_COLOR, Boolean.FALSE}
                });
                availableActions.put(Action.REM, new Object[]{new Object[]{Action.Variable.GROUP_NAME, Boolean.TRUE}});
                availableActions.put(Action.EDIT, new Object[]{
                        new Object[]{Action.Variable.GROUP_NAME, Boolean.TRUE},
                        new Object[]{Action.Variable.GROUP_COLOR, Boolean.FALSE}
                });
                availableActions.put(Action.VIEW, new Object[]{new Object[]{Action.Variable.GROUP_NAME, Boolean.TRUE}});
                availableActions.put(Action.LIST, new Object[]{new Object[]{Action.Variable.NONE, Boolean.FALSE}});
            }
        };

        protected HashMap<Action, Object[]> availableActions;
        protected void init() {}
        Entity() {
            init();
        }

        private static boolean needVarsForAction(Entity e, Action a) {
            Object[] varsWithState = e.availableActions.get(a);
            for (int i = 0; i < varsWithState.length; i++) {
                Object[] varWithState = (Object[]) varsWithState[i];
                if (varWithState[0].equals(Action.Variable.NONE)) return false;
            }
            return true;
        }

        private Action getAction(String name) throws IncorrectInputException {
            Action a = Action.valueOf(name);
            if (availableActions.containsKey(a)) {
                return a;
            } else {
                throw new IncorrectInputException();
            }
        }
    }

    public void listen(String command) {
        if (command.equals("help")) {
            StringBuilder sb = new StringBuilder();
            sb.append("--->--->example(space is separator): [Entity] [Action] [[var name] [var value]...]\n");

            for (int i = 0; i < Entity.values().length; i++) {
                Entity e = Entity.values()[i];
                sb.append("--->").append(e.name().toLowerCase()).append("<---").append("\n");
                Action[] keys = new Action[e.availableActions.size()];
                e.availableActions.keySet().toArray(keys);
                for (int j = 0; j < keys.length; j++) {
                    Action a = keys[j];
                    sb.append("--->--->").append(a.help()).append("\n");
                    Object[] variablesWithState = e.availableActions.get(a);
                    for (int k = 0; k < variablesWithState.length; k++) {
                        Object[] variableWithState = ((Object[]) variablesWithState[k]);
                        sb.append("--->--->--->").append(((Action.Variable)variableWithState[0]).help()).append("\n");
                    }
                }
            }

            StorageModel.INSTANCE.applyString(sb.toString());
            return;
        }
        String[] split = command.split(" ");

        if (split.length < 2) {
            StorageModel.INSTANCE.applyException(new IncorrectInputException());
            return;
        }
        String who = split[0].toUpperCase();
        String what = split[1].toUpperCase();

        Entity e = null;
        Action a = null;

        try {
            e = Entity.valueOf(who);
            a = Action.valueOf(what);
        } catch (IllegalArgumentException e1) {
            StorageModel.INSTANCE.applyException(e1);
            return;
        }

        HashMap<Action.Variable, Object> inputVariables = new HashMap<>();

        if (Entity.needVarsForAction(e, a)) {
            for (int i = 2; i < split.length; i += 2) {
                if (i%2 != 0) continue;
                if (i == split.length - 1) break;
                Action.Variable v = null;
                try {
                    v = Action.Variable.getByShortName(split[i]);
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
            Object[] varsWithState = e.availableActions.get(a);
            for (int i = 0; i < varsWithState.length; i++) {
                Object[] varWithState = (Object[]) varsWithState[i];
                if (!((boolean) varWithState[1])) continue;
                if (!inputVariables.containsKey((Action.Variable) varWithState[0])) {
                    StorageModel.INSTANCE.applyException(new IncorrectInputException("\nvariable " +
                            ((Action.Variable) varWithState[0]).getShortName() + " not exists"));
                    return;
                }
            }
        } else if (split.length > 2) {
            StorageModel.INSTANCE.applyException(new IncorrectInputException());
            return;
        }

        StorageModel.INSTANCE.apply(e, a, inputVariables);
    }

}
