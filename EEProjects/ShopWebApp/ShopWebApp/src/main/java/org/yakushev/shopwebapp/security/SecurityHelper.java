package org.yakushev.shopwebapp.security;

import java.util.Arrays;
import java.util.List;

public class SecurityHelper {
    private static final List<String> PUBLIC_URL_LIST =
            Arrays.asList("/login", "/signup");

    public static boolean isPublicUrl(String servletPath) {
        return PUBLIC_URL_LIST.contains(servletPath);
    }
}
