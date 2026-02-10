package com.iut.banque.compte.domain.usecase;

import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.compte.domain.exceptions.AccesCompteInterditException;
import com.iut.banque.compte.domain.exceptions.CompteNotFoundException;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compte.domain.entity.Compte;
import com.iut.banque.utilisateur.domain.catalog.UtilisateurCatalog;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompteUseCase {

    private static final Logger log =
            LoggerFactory.getLogger(CompteUseCase.class);

    private final CompteCatalog compteCatalog;
    private final UtilisateurCatalog utilisateurCatalog;

    @Transactional(readOnly = true)
    public Compte obtenirCompteClient(Client client, String compteId) {

        log.info("Consultation du compte id={} par clientId={}",
                compteId, client.getUserId());

        Compte compte = compteCatalog.obtenirCompteParId(compteId)
                .orElseThrow(() -> {
                    log.warn("Compte introuvable id={} pour clientId={}",
                            compteId, client.getUserId());
                    return new CompteNotFoundException(compteId);
                });

        if (!compte.getOwner().equals(client)) {
            log.warn("Tentative d'accès non autorisé compteId={} clientId={} ownerId={}",
                    compteId,
                    client.getUserId(),
                    compte.getOwner().getUserId());

            throw new AccesCompteInterditException(compteId, client.getUserId());
        }

        log.debug("Accès autorisé au compte id={} pour clientId={}",
                compteId, client.getUserId());

        return compte;
    }
}
