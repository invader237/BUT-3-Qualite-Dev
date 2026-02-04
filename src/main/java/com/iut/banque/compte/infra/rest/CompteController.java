package com.iut.banque.compte.infra.rest;

import com.iut.banque.api.ComptesApi;
import com.iut.banque.client.domain.catalog.ClientCatalog;
import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.compte.domain.command.CrediterCompteCommand;
import com.iut.banque.compte.domain.command.DebiterCompteCommand;
import com.iut.banque.compte.domain.usecase.CrediterCompteUseCase;
import com.iut.banque.compte.domain.usecase.DebiterCompteUseCase;
import com.iut.banque.compte.infra.mapper.CompteToCompteDtoMapper;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compte.domain.entity.Compte;
import com.iut.banque.compte.domain.usecase.CompteUseCase;
import com.iut.banque.model.CompteDto;
import com.iut.banque.model.CreateUtilisateurRequest;
import com.iut.banque.model.UtilisateurDto;
import com.iut.banque.shared.auth.infra.GetUser;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comptes")
@RequiredArgsConstructor
public class CompteController implements ComptesApi {

    private final CompteUseCase compteUseCase;
    private final CompteCatalog compteCatalog;
    private final ClientCatalog clientCatalog;
    private final GetUser getUser;
    private final DebiterCompteUseCase debiterCompteUseCase;
    private final CrediterCompteUseCase crediterCompteUseCase;
    private final CompteToCompteDtoMapper compteToCompteDtoMapper;

    @Override
    public void debiterCompte(String id, Double montant) {
        DebiterCompteCommand command =
                new DebiterCompteCommand(id, montant);

        try {
            debiterCompteUseCase.handle(command);
        } catch (InsufficientFundsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Fonds insuffisants");
        } catch (IllegalFormatException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Montant invalide");
        } catch (com.iut.banque.exceptions.IllegalFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void crediterCompte(String id, Double montant) {
        CrediterCompteCommand command =
                new CrediterCompteCommand(id, montant);

        try {
            crediterCompteUseCase.handle(command);
        } catch (IllegalFormatException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Montant invalide");
        } catch (com.iut.banque.exceptions.IllegalFormatException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public List<CompteDto> getAllComptes() {
        List<Compte> comptes = compteCatalog.obtenirToutLesComptes();
        return comptes.stream()
                .map(compteToCompteDtoMapper)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public CompteDto getCompteClient(String compteId) {
        Utilisateur utilisateur = getUser.apply();
        Optional<Client> client = clientCatalog.obtenirClientParId(utilisateur.getUserId());
        if (client.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Client non trouvé");
        }
        Compte compte = compteUseCase.obtenirCompteClient(client.get(), compteId);
        return compteToCompteDtoMapper.apply(compte);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public List<CompteDto> getComptesClient(String id) {
        Utilisateur utilisateur = getUser.apply();
        Optional<Client> client = clientCatalog.obtenirClientParId(utilisateur.getUserId());
        if (client.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Client non trouvé");
        }
        List<Compte> comptes = compteCatalog.obtenirComptesParClient(client.get());
        return comptes.stream()
                .map(compteToCompteDtoMapper)
                .toList();
    }

}
