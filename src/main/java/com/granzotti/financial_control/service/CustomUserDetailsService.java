package com.granzotti.financial_control.service;

import com.granzotti.financial_control.model.User;
import com.granzotti.financial_control.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo nome de usuário
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o nome: " + username);
        }

        // Converte o usuário encontrado para um objeto UserDetails, necessário para o Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // A senha já deve estar criptografada no banco
                .authorities("USER") // Define a autoridade padrão (pode ser ajustada para múltiplos papéis/roles)
                .build();
    }
}
