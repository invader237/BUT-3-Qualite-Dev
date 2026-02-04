package com.iut.banque.compte.domain.usecase;

import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compte.domain.command.CrediterCompteCommand;
import com.iut.banque.compte.domain.entity.Compte;
import com.iut.banque.compte.domain.exceptions.CompteNotFoundException;
import com.iut.banque.exceptions.IllegalFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CrediterCompteUseCase {

    private final CompteCatalog compteCatalog;

    @Transactional
    public void handle(CrediterCompteCommand command) throws IllegalFormatException {
        Compte compte = compteCatalog
                .obtenirCompteParId(command.compteId())
                .orElseThrow(() -> new CompteNotFoundException(command.compteId()));

        compte.crediter(command.montant());
        compteCatalog.enregistrerCompte(compte);
    }
}
