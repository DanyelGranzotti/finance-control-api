package com.granzotti.financial_control.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Chave secreta usada para assinar o token (recomenda-se que seja configurada no arquivo de propriedades)
    @Value("${jwt.secret}")
    private String secret;

    // Tempo de expiração do token (em milissegundos)
    @Value("${jwt.expiration}")
    private Long jwtExpirationInMillis;

    // Gera um token JWT com as informações do usuário
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Cria o token com as reivindicações (claims) e o tempo de expiração
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // O "subject" aqui é o nome do usuário
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data de criação
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis)) // Data de expiração
                .signWith(SignatureAlgorithm.HS256, secret) // Assina o token com HMAC-SHA256 usando a chave secreta
                .compact();
    }

    // Extrai o nome de usuário (subject) de um token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrai qualquer tipo de informação do token usando uma função
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Método privado para extrair todas as reivindicações do token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Verifica se o token JWT expirou
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extrai a data de expiração de um token JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Valida se o token pertence ao usuário e se ainda não expirou
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
