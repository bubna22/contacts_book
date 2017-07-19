package com.bubna.controller;

import com.bubna.model.StorageModel;
import com.bubna.exceptions.IncorrectInputException;

import java.util.HashMap;

/**
 * Created by test on 11.07.2017.
 */
public enum CommandController {

    /**
     * CommandController; part of MVC pattern; used enum for realization of singleton
     */

    INSTANCE;

    /**
     * available actions in contacts_book
     */
    public enum Action {
        ADD("add entity"),
        REM("remove entity"),
        EDIT("add entity"),
        VIEW("get properties of entity"),
        LIST("list entities");

        /**
         * string, which shows when "help" is calling by user
         */
        private String help;

        /**
         * available variables for action for contacts_book
         */
        public enum Variable {
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

            /**
             * short name for ui
             */
            private String shortName;
            /**
             * available input class for variable
             */
            protected Class<?> availInput;
            /**
             * string with description of Variable
             */
            protected String help;

            /**
             * getter for short name
             * @return shortname
             */
            protected String getShortName() {
                return shortName;
            }

            /**
             * called when user input "help"
             * @return help string
             */
            protected String help() {
                return "Variable name: " + shortName + "; Description: " + help;
            }

            /**
             * checker of inputting value for variable
             * @param o
             * @return inputted obj
             * @throws IncorrectInputException
             */
            protected Object parseVariable(Object o) throws IncorrectInputException {
                if (o.getClass().equals(availInput)) return o;
                throw new IllegalArgumentException();
            }

            /**
             * static method for serching correct variable
             * @param shortName
             * @return correct variable
             * @throws IncorrectInputException
             */

            static Variable getByShortName(String shortName) throws IncorrectInputException {
                if (shortName.equals(NONE.getShortName())) throw new IllegalArgumentException("\nno vars available");
                for (int i = 0; i < Variable.values().length; i++) {
                    Variable v = Variable.values()[i];
                    if (shortName.equals(v.getShortName())) return v;
                }
                throw new IncorrectInputException("\nno such variable");
            }

            /**
             * enum's constructor
             * @param shortName
             * @param helpStr
             * @param availInput
             */
            Variable(String shortName, String helpStr, Class<?> availInput) {
                this.shortName = shortName;
                this.help = helpStr;
                this.availInput = availInput;
            }
        }

        /**
         * enums constructor
         * @param helpStr
         */
        Action(String helpStr) {
            this.help = helpStr;
        }

        /**
         * call, when user need help:)
         * @return
         */
        String help() {
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

        /**
         * available actions for entity with available variables
         */
        protected HashMap<Action, Object[]> availableActions;

        /**
         * call for initilize HashMap availableActions
         */
        protected void init() {}
        Entity() {
            init();
        }

        /**
         * check if need var for action of the entity
         * @param e
         * @param a
         * @return boolean
         */
        static boolean needVarsForAction(Entity e, Action a) {
            Object[] varsWithState = e.availableActions.get(a);
            for (int i = 0; i < varsWithState.length; i++) {
                Object[] varWithState = (Object[]) varsWithState[i];
                if (varWithState[0].equals(Action.Variable.NONE)) return false;
            }
            return true;
        }
    }

    private CommandHandler cmdHandler;

    CommandController() {
        cmdHandler = new HelpCommandHandler();
        cmdHandler.setNext(new MainCommandHandler());
    }

    /**
     * method-parser of user's cmd
     * @param command
     */
    public void listen(String command) {
        cmdHandler.handle(command);
    }

}
