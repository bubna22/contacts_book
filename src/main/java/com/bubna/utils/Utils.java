package com.bubna.utils;

import com.bubna.Init;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by test on 11.07.2017.
 */
public class Utils {

    public static File getRootDir() throws URISyntaxException {
        return new File(Utils.class.getResource("/storage").getFile());
    }
}
