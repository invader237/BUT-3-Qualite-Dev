package com.iut.banque.shared.auth.infra;

import com.iut.banque.shared.auth.domain.entity.GetEntity;
import com.iut.banque.utilisateur.domain.catalog.UtilisateurCatalog;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUser implements GetEntity<Utilisateur> {

    private final UtilisateurCatalog utilisateurCatalog;

    @Override
    public Utilisateur apply() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return utilisateurCatalog.obtenirUtilisateurParUtilisateurId(username);

    }
}
