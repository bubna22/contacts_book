package com.bubna.utils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by test on 11.07.2017.
 */
public class Utils {

    public static String getJarDir() throws URISyntaxException {
        return new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath();
    }

    public static File getRootDir() throws URISyntaxException {
        return new File(Utils.class.getResource("/storage").getFile());
    }

    public static File getDataXml() throws URISyntaxException {
        return new File(Utils.class.getResource("/storage/data.xml").getFile());
    }

    public static File getDataXsd() throws URISyntaxException {
        return new File(Utils.class.getResource("/schemas/data.xsd").getFile());
    }
}
