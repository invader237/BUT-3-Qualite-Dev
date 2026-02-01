package com.iut.banque.utilisateur.domain.catalog;

import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.gestionnaire.domain.entity.Gestionnaire;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurCatalog {

    void supprimerUtilisateur(Utilisateur utilisateur);

    void enregistrerUtilisateur(Utilisateur utilisateur);

    Optional<Utilisateur> obtenirUtilisateurParId(String id);

    List<Client> obtenirToutLesClients();

    List<Gestionnaire> obtenirToutLesGestionnaires();

    boolean existeParUserId(String username);

    Utilisateur obtenirUtilisateurParUtilisateurId(String id);

}
