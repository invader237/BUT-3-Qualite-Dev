package com.iut.banque.shared.auth.domain.service;

import com.iut.banque.shared.auth.configuration.PasswordService;
import com.iut.banque.utilisateur.domain.catalog.UtilisateurCatalog;

import com.iut.banque.utilisateur.domain.command.ChangePasswordCommand;
import com.iut.banque.utilisateur.domain.command.RegisterUserCommand;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.utilisateur.domain.exceptions.AuthenticationFailedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurCatalog utilisateurCatalog;
    private final PasswordEncoder passwordEncoder;
    private final PasswordService passwordService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.auth}")
    private long jwtExpirationAuth;

    @Value("${jwt.expiration.reset}")
    private long jwtExpirationReset;
    private Key key;

    private final Set<String> usedTokens = new CopyOnWriteArraySet<>();

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private List<GrantedAuthority> buildAuthorities(Utilisateur user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user instanceof com.iut.banque.client.domain.entity.Client) {
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        }

        if (user instanceof com.iut.banque.gestionnaire.domain.entity.Gestionnaire) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }

        return authorities;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            if (usedTokens.contains(token)) {
                throw new RuntimeException("Le token a déjà été utilisé.");
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void markTokenAsUsed(String token) {
        usedTokens.add(token);
    }

    @Scheduled(fixedRate = 3600000) // Toutes les heures
    public void cleanUsedTokens() {
        usedTokens.clear();
    }

    private String extractEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean emailExists(String email) {
        return utilisateurCatalog.existeParUserId(email.toLowerCase());
    }

    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurCatalog.obtenirUtilisateurParUtilisateurId(email.toLowerCase());
        // .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec cet email."));
    }

    public void registerUser( RegisterUserCommand registerUserCommand ) {
        log.info("Register attempt for userId={}", registerUserCommand.userId());

        String hashedPassword = passwordEncoder.encode(registerUserCommand.password());

        try {
            Client client = new Client();
            client.setUserId(registerUserCommand.userId());
            client.setUserPwd(hashedPassword);
            client.setPrenom(registerUserCommand.prenom());
            client.setNom(registerUserCommand.nom());
            client.setAdresse(registerUserCommand.adresse());
            client.setMale(registerUserCommand.male());
            client.setNumeroClient(registerUserCommand.numClient());

            utilisateurCatalog.enregistrerUtilisateur(client);

            log.info("Registration successful for userId={}", registerUserCommand.userId());
        } catch (IllegalArgumentException | com.iut.banque.exceptions.IllegalFormatException e) {
            log.error("Registration failed while creating client for userId={}", registerUserCommand.userId(), e);
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public Utilisateur authenticateUser(String userId, String password) {

        log.info("Authentication attempt for userId={}", userId);

        if (!utilisateurCatalog.existeParUserId(userId.toLowerCase())) {
            log.warn("Authentication failed: user not found={}", userId);
            throw new AuthenticationFailedException();
        }

        Utilisateur user = utilisateurCatalog.obtenirUtilisateurParUtilisateurId(userId.toLowerCase());

        if (!passwordEncoder.matches(password, user.getUserPwd())) {
            log.warn("Authentication failed: bad credentials for userId={}", userId);
            throw new AuthenticationFailedException();
        }

        List<GrantedAuthority> authorities = buildAuthorities(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Authentication successful for userId={}", userId);

        return user;
    }

    public String generateAuthToken(Utilisateur user) {

        List<String> roles = new ArrayList<>();

        if (user instanceof com.iut.banque.client.domain.entity.Client) {
            roles.add("ROLE_CLIENT");
        }

        if (user instanceof com.iut.banque.gestionnaire.domain.entity.Gestionnaire) {
            roles.add("ROLE_MANAGER");
        }

        log.debug("Generating auth token for user={}", user.getUserId());

        return Jwts.builder()
                .setSubject(user.getUserId())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationAuth))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public void changePassword(Utilisateur utilisateur, String newPassword) {
        String hashedPassword = passwordEncoder.encode(newPassword);
        utilisateur.setUserPwd(hashedPassword);
        utilisateurCatalog.enregistrerUtilisateur(utilisateur);
    }
}