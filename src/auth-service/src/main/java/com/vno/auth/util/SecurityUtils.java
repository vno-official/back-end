package com.vno.auth.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtils {
    
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String ROLES_HEADER = "X-Role";

    private SecurityUtils() {
        // Utility class
    }

    public static String getCurrentUserId() {
        return getRequestHeader(USER_ID_HEADER);
    }

    public static String getCurrentUserRoles() {
        return getRequestHeader(ROLES_HEADER);
    }

    public static boolean hasRole(String role) {
        String roles = getCurrentUserRoles();
        return roles != null && roles.contains(role);
    }

    private static String getRequestHeader(String headerName) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            return request.getHeader(headerName);
        } catch (Exception e) {
            return null;
        }
    }
}
