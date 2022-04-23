package org.yakushev.shopwebapp.security;

import java.util.Arrays;
import java.util.List;

public class SecurityHelper {
    private static final List<String> PUBLIC_URL_LIST =
            Arrays.asList("/login", "/signup", "/api/login", "/api/signup");

    public static boolean isPublicUrl(String servletPath) {
        for (String publicUrl : PUBLIC_URL_LIST) {
            if (servletPath.equals(publicUrl)) {
                return true;
            }
        }

        return false;
    }
}
