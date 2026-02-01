package com.iut.banque.utilisateur.infra.rest;

import com.iut.banque.shared.auth.domain.service.AuthService;
import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compte.domain.usecase.CompteUseCase;
import com.iut.banque.shared.auth.infra.GetUser;
import com.iut.banque.utilisateur.domain.catalog.UtilisateurCatalog;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurCatalog utilisateurCatalog;
    private final CompteUseCase compteUseCase;
    private final CompteCatalog compteCatalog;
    private final AuthService authService;

    private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);
    private final GetUser getUser;

    @PostMapping("/me")
    public Utilisateur me() {
        return getUser.apply();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String numeroClient,
            @RequestParam String numeroCompte,
            @RequestParam String userId,
            @RequestParam String userPwd,
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam boolean male
    ) {

        log.info("Register request for email={}", userId);
        try {
            authService.registerUser(
                    numeroClient,
                    numeroCompte,
                    userId,
                    userPwd,
                    nom,
                    prenom,
                    male
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            log.warn("Register failed for email={}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Register error for email={}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String userId,
            @RequestParam String password
    ) {
        log.info("Login request for userId={}", userId);
        try {
            Utilisateur utilisateur = authService.authenticateUser(userId, password);
            String token = authService.generateAuthToken(utilisateur);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            log.warn("Login failed for userId={}", userId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        log.info("Logout request");
        try {
            String token = authHeader.replace("Bearer ", "");
            //authService.invalidateToken(token);
            return ResponseEntity.ok("Déconnexion réussie");
        } catch (Exception e) {
            log.error("Logout error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {
        log.info("Change password request");
        try {
            String token = authHeader.replace("Bearer ", "");
            //authService.changePassword(token, oldPassword, newPassword);
            return ResponseEntity.ok("Mot de passe changé avec succès");
        } catch (IllegalArgumentException e) {
            log.warn("Change password failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Change password error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }
}
