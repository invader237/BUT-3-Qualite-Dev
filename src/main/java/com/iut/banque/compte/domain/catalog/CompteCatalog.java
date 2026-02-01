package com.iut.banque.compte.domain.catalog;

import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.compte.domain.entity.Compte;

import java.util.List;
import java.util.Optional;

public interface CompteCatalog {

    void enregistrerCompte(Compte compte);

    void supprimerCompte(Compte compte);

    List<Compte> obtenirComptesParClient(Client client);

    Optional<Compte> obtenirCompteParId(String id);

    List<Compte> obtenirToutLesComptes();
}
