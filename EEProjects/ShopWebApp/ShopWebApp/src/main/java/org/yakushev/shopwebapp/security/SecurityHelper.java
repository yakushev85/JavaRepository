package org.yakushev.shopwebapp.security;

import java.util.Arrays;
import java.util.List;

public class SecurityHelper {
    private static final List<String> PUBLIC_URL_LIST =
            Arrays.asList("/", "/api/login", "/api/signup");
    private static final List<String> PUBLIC_EXT_LIST =
            Arrays.asList(".css", ".js", ".txt", ".html", ".ico");

    public static boolean isPublicUrl(String servletPath) {
        if (PUBLIC_URL_LIST.contains(servletPath)) {
            return true;
        }

        for (String currentExt : PUBLIC_EXT_LIST) {
            if (servletPath.endsWith(currentExt)) {
                return true;
            }
        }

        return false;
    }
}
