package org.oiakushev.hwjbackendwizard.util;

import java.util.HashMap;

public class StringNamingUtil {
    public static String toFirsLetterLower(String inString) {
        if (inString.isEmpty()) {
            return inString;
        } else if (inString.length() == 1) {
            return inString.toLowerCase();
        } else {
            return inString.substring(0, 1).toLowerCase() + inString.substring(1);
        }
    }

    public static String toFirstLetterUpper(String inString) {
        if (inString.isEmpty()) {
            return inString;
        } else if (inString.length() == 1) {
            return inString.toUpperCase();
        } else {
            return inString.substring(0, 1).toUpperCase() + inString.substring(1);
        }
    }

    public static String filterLine(String inLine, HashMap<String, String> inMaskMap) {
        String result = inLine;

        for (String key : inMaskMap.keySet()) {
            result = result.replaceAll("\\|" + key + "\\|", inMaskMap.get(key));
        }

        return result;
    }

}
