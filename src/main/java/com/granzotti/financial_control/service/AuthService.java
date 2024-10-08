package com.granzotti.financial_control.service;

import com.granzotti.financial_control.dto.AuthRequest;
import com.granzotti.financial_control.dto.ResetPasswordRequest;
import com.granzotti.financial_control.model.User;
import com.granzotti.financial_control.repository.UserRepository;
import com.granzotti.financial_control.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> register(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);

        return ResponseEntity.ok("Usuário registrado com sucesso");
    }

    public ResponseEntity<?> login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Usuário ou senha incorretos");
        }

        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(token);
    }

    public ResponseEntity<?> resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }

        String resetToken = jwtService.generateToken(user.getUsername());
        String subject = "Recuperação de Senha";
        String body = "Use o seguinte token para resetar sua senha: " + resetToken;
        emailService.sendSimpleEmail(user.getEmail(), subject, body);

        return ResponseEntity.ok("Instruções para redefinição de senha enviadas para o e-mail");
    }
}
