package com.bubna.frontend;

import com.bubna.backend.CommandController;
import com.bubna.exceptions.InitException;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by test on 12.07.2017.
 */
public class UIConsole implements UIConnectable {

    private Scanner scanner;

    public UIConsole() {
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("input command like '[object]/[action]' (for more info input 'contacts_book/help'):");
            try {
                System.out.println(CommandController.INSTANCE.listen(this));
            } catch (InitException e) {
                System.out.println("Error while initializing; Exception message: " + e.getCustomMsg());
                System.exit(1);
            }
        }
    }

    @Override
    public String getAnswer(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    @Override
    public int getIntAnswer(String question) {
        while (true) {
            System.out.println(question);
            try {
                return scanner.nextInt();
            } catch (NoSuchElementException e) {
                System.out.println("incorrect input");
                scanner.next();
            }
        }
    }
}
