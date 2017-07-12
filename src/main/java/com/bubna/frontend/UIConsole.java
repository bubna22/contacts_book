package com.bubna.frontend;

import com.bubna.backend.CommandController;

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
            System.out.println(CommandController.getInstance().listen(this));
        }
    }

    @Override
    public String getAnswer(String question) {
        System.out.println(question);
        return scanner.next();
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
