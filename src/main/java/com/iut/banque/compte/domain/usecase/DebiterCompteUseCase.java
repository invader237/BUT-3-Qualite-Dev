package com.iut.banque.compte.domain.usecase;

import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compte.domain.command.DebiterCompteCommand;
import com.iut.banque.compte.domain.entity.Compte;
import com.iut.banque.compte.domain.exceptions.CompteNotFoundException;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DebiterCompteUseCase {

    private static final Logger log =
            LoggerFactory.getLogger(DebiterCompteUseCase.class);

    private final CompteCatalog compteCatalog;

    @Transactional
    public void handle(DebiterCompteCommand command)
            throws InsufficientFundsException, IllegalFormatException {

        log.info("Débit du compte id={} montant={}",
                command.compteId(), command.montant());

        Compte compte = compteCatalog
                .obtenirCompteParId(command.compteId())
                .orElseThrow(() -> {
                    log.warn("Compte introuvable id={}", command.compteId());
                    return new CompteNotFoundException(command.compteId());
                });

        compte.debiter(command.montant());

        compteCatalog.enregistrerCompte(compte);

        log.info("Débit effectué avec succès compteId={} nouveauSolde={}",
                compte.getNumeroCompte(), compte.getSolde());
    }
}
