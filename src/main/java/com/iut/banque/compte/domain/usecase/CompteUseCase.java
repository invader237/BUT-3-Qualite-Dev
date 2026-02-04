package com.iut.banque.compte.domain.usecase;

import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compte.domain.entity.Compte;
import com.iut.banque.utilisateur.domain.catalog.UtilisateurCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompteUseCase {

    private final CompteCatalog compteCatalog;
    private final UtilisateurCatalog utilisateurCatalog;

    @Transactional
    public Compte obtenirCompteClient(Client client, String compteId) {
        Optional<Compte> compte = compteCatalog.obtenirCompteParId(compteId);
        if (compte.isEmpty() || !compte.get().getOwner().equals(client)) {
            throw new IllegalArgumentException("Compte non trouvé pour ce client");
        }
        return compte.orElse(null);
    }


}
