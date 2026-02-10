package com.iut.banque.compte.domain.usecase;

import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compte.domain.command.CrediterCompteCommand;
import com.iut.banque.compte.domain.entity.Compte;
import com.iut.banque.compte.domain.exceptions.CompteNotFoundException;
import com.iut.banque.exceptions.IllegalFormatException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CrediterCompteUseCase {

    private static final Logger log =
            LoggerFactory.getLogger(CrediterCompteUseCase.class);

    private final CompteCatalog compteCatalog;

    @Transactional
    public void handle(CrediterCompteCommand command)
            throws IllegalFormatException {

        log.info("Crédit du compte id={} montant={}",
                command.compteId(), command.montant());

        Compte compte = compteCatalog
                .obtenirCompteParId(command.compteId())
                .orElseThrow(() -> {
                    log.warn("Compte introuvable lors du crédit id={}",
                            command.compteId());
                    return new CompteNotFoundException(command.compteId());
                });

        compte.crediter(command.montant());

        compteCatalog.enregistrerCompte(compte);

        log.info("Crédit effectué avec succès compteId={} nouveauSolde={}",
                compte.getNumeroCompte(), compte.getSolde());
    }
}