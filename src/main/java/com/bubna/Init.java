package com.bubna;

import com.bubna.backend.CommandController;

/**
 * Created by test on 11.07.2017.
 */
public class Init {

    public Init() {

    }

    public static void main(String[] args) {
        System.out.println("work hard, don't play");
        CommandController.getInstance().listen();
    }

}
