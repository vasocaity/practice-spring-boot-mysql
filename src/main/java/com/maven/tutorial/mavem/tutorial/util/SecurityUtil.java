package com.maven.tutorial.mavem.tutorial.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {
    public static Optional<Integer> getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return Optional.empty();
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }

        Object principal = authentication.getName();
        if (principal == null) {
            return Optional.empty();
        }

        Integer userId = Integer.parseInt(principal.toString());
        return Optional.of(userId);
    }
}
