package com.iut.banque.compte.infra.rest;

import com.iut.banque.client.domain.catalog.ClientCatalog;
import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compte.domain.entity.Compte;
import com.iut.banque.compte.domain.usecase.CompteUseCase;
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
public class CompteController {

    private final CompteUseCase compteUseCase;
    private final CompteCatalog compteCatalog;
    private final ClientCatalog clientCatalog;
    private final GetUser getUser;

    @PostMapping("/{id}/debiter")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_MANAGER')")
    public ResponseEntity<String> debiter(
            @PathVariable String id,
            @RequestParam double montant) {

        // Récupération du compte
        Compte compte = compteCatalog.obtenirCompteParId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Compte non trouvé"));

        try {
            // Use case : on passe l'objet direct
            compteUseCase.debiterCompte(compte, montant);
            return ResponseEntity.ok("Débit effectué");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Fonds insuffisants");
        } catch (IllegalFormatException e) {
            return ResponseEntity.badRequest()
                    .body("Montant invalide");
        } catch (com.iut.banque.exceptions.IllegalFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_MANAGER')")
    @PostMapping("/{id}/crediter")
    public ResponseEntity<String> crediter(
            @PathVariable String id,
            @RequestParam double montant) {

        // Récupération du compte
        Compte compte = compteCatalog.obtenirCompteParId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Compte non trouvé"));

        try {
            // Use case : on passe l'objet direct
            compteUseCase.crediterCompte(compte, montant);
            return ResponseEntity.ok("Crédit effectué");
        } catch (IllegalFormatException e) {
            return ResponseEntity.badRequest()
                    .body("Montant invalide");
        } catch (com.iut.banque.exceptions.IllegalFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<Compte>> getAllComptes() {
        List<Compte> comptes = compteCatalog.obtenirToutLesComptes();
        return ResponseEntity.ok(comptes);
    }

    @GetMapping("/client/{id}/all")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<List<Compte>> getCompteById(@PathVariable String id) {
        Utilisateur utilisateur = getUser.apply();
        Optional<Client> client = clientCatalog.obtenirClientParId(utilisateur.getUserId());
        List<Compte> comptes = compteCatalog.obtenirComptesParClient(client.orElse(null));
        return ResponseEntity.ok(comptes);
    }

    @GetMapping("/client/{compteId}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<Compte> getCompteClient(
            @PathVariable String compteId) {

        Utilisateur utilisateur = getUser.apply();
        Client client = clientCatalog.obtenirClientParId(utilisateur.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Client non authentifié"));

        try {
            Compte compte =
                    compteUseCase.obtenirCompteClient(client, compteId);
            return ResponseEntity.ok(compte);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
