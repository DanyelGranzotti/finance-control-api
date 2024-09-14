package com.granzotti.financial_control.util;

import com.granzotti.financial_control.model.User;
import com.granzotti.financial_control.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private final UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            } else if (principal instanceof String) {
                return userRepository.findByUsername((String) principal)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            }
        }

        throw new RuntimeException("Usuário não autenticado");
    }
}
