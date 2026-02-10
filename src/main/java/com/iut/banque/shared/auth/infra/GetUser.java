package com.iut.banque.shared.auth.infra;

import com.iut.banque.shared.auth.domain.entity.GetEntity;
import com.iut.banque.utilisateur.domain.catalog.UtilisateurCatalog;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUser implements GetEntity<Utilisateur> {

    private static final Logger log =
            LoggerFactory.getLogger(GetUser.class);

    private final UtilisateurCatalog utilisateurCatalog;

    @Override
    public Utilisateur apply() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Tentative d'accès sans authentification valide");
            throw new IllegalStateException("Utilisateur non authentifié");
        }

        String username = authentication.getName();

        log.debug("Utilisateur authentifié détecté username={}", username);

        Utilisateur utilisateur =
                utilisateurCatalog.obtenirUtilisateurParUtilisateurId(username);

        if (utilisateur == null) {
            log.error("Utilisateur authentifié introuvable en base username={}", username);
            throw new IllegalStateException("Utilisateur introuvable");
        }

        return utilisateur;
    }
}
