package com.iut.banque.client.infra.repository;

import com.iut.banque.client.domain.catalog.ClientCatalog;
import com.iut.banque.client.domain.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientHibernateAdapter implements ClientCatalog {

    private final ClientRepository clientRepository;

    @Override
    public Optional<Client> obtenirClientParId(String id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client rechercherParUserId(String clientId) {
        return clientRepository.findById(clientId).orElseThrow();
    }

}
