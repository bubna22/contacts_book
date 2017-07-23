package com.bubna.view.javaFX;

import javafx.scene.control.TextArea;

/**
 * Created by test on 15.07.2017.
 */
public class Console extends TextArea {

    private Commanding commanding;
    private boolean consoleModeEnabled;
    private String consoleSymbol = ">:";
    private String enterCmdSymbol = "\n";

    public Console(Commanding c) {
        this.appendText(consoleSymbol);
        this.commanding = c;
        setWrapText(true);
        appendText("contacts_book powered by bubna\n      input help for help. Good luck:)\n>:");
        consoleModeEnabled = true;
    }

    public void setConsoleMode(boolean enabled) {
        consoleModeEnabled = enabled;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        //if console disabled, make console default
        if (!consoleModeEnabled) {
            super.replaceText(start, end, text);
            return;
        }
        String current = getText();
        String[] splitted = current.split(enterCmdSymbol);

        //cmd entered
        if (text.equals(enterCmdSymbol) && (splitted.length > 0 && splitted[splitted.length - 1].startsWith(consoleSymbol)) ||
                (splitted.length == 0 && current.startsWith(consoleSymbol))) {
            commanding.onEnterCmd(splitted[splitted.length - 1].replace(consoleSymbol, ""));
            setText(getText() + enterCmdSymbol + consoleSymbol);
            appendText("");
            return;
        }

        //check accesses to modify/delete chars in cur pos (need to simplify)
        if ((splitted.length < 2 &&
                (current.startsWith(consoleSymbol) && ((current.length() > 2 && end > 2) ||
                        (start == 2 && start == end)))) ||
            (splitted.length >= 2 &&
                (splitted[splitted.length - 1].startsWith(consoleSymbol) && ((splitted[splitted.length - 1].length() > 2
                        && end > current.length() - splitted[splitted.length - 1].length() + 2) ||
                        (start == current.length() - splitted[splitted.length - 1].length() + 2 && start == end))))) {
            super.replaceText(start, end, text.equals(enterCmdSymbol)?text + consoleSymbol:text);
        }
    }
    @Override
    public void replaceSelection(String text) {
        String current = getText();
        int selectionStart = getSelection().getStart();
        if (! current.substring(selectionStart).contains(enterCmdSymbol)) {
            super.replaceSelection(text);
        }
    }

    public interface Commanding {
        void onEnterCmd(String s);
    }
}
