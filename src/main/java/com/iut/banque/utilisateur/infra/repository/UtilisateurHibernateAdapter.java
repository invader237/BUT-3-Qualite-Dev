package com.iut.banque.utilisateur.infra.repository;

import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.gestionnaire.domain.entity.Gestionnaire;
import com.iut.banque.utilisateur.domain.catalog.UtilisateurCatalog;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UtilisateurHibernateAdapter implements UtilisateurCatalog {
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public void supprimerUtilisateur(Utilisateur utilisateur) {
        utilisateurRepository.delete(utilisateur);
    }

    @Override
    public void enregistrerUtilisateur(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public Optional<Utilisateur> obtenirUtilisateurParId(String id) {
        return utilisateurRepository.getUtilisateurByUserId(id);
    }

    @Override
    public List<Client> obtenirToutLesClients() {
        return utilisateurRepository.findAllClients();
    }

    @Override
    public List<Gestionnaire> obtenirToutLesGestionnaires() {
        return utilisateurRepository.findAllGestionnaires();
    }

    @Override
    public boolean existeParUserId(String username) {
        return utilisateurRepository.existsByUserId(username);
    }

    @Override
    public Utilisateur obtenirUtilisateurParUtilisateurId(String id) {
        return utilisateurRepository.getUtilisateurByUserId(id).orElseThrow();
    }
}
