package com.iut.banque.compte.infra.repository;

import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compte.domain.entity.Compte;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompteHibernateAdapter implements CompteCatalog {

    private final CompteRepository compteRepository;

    @Override
    public void enregistrerCompte(Compte compte) {
        compteRepository.save(compte);
    }

    @Override
    public void supprimerCompte(Compte compte) {
        compteRepository.delete(compte);
    }

    @Override
    public List<Compte> obtenirComptesParClient(Client client) {
        return compteRepository.findAllByOwner(client);
    }

    @Override
    public Optional<Compte> obtenirCompteParId(String id) {
        return compteRepository.findById(id);
    }

    @Override
    public List<Compte> obtenirToutLesComptes() {
        return compteRepository.findAll();
    }
}
