package com.bubna.utils;

import com.bubna.Init;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by test on 11.07.2017.
 */
public class Utils {

    public static File getRootDir() {
        try {
            return new File(Init.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
