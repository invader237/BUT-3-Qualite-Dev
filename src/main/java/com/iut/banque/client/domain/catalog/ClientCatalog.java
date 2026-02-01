package com.iut.banque.client.domain.catalog;

import com.iut.banque.client.domain.entity.Client;

import java.util.Optional;

public interface ClientCatalog {

    Optional<Client> obtenirClientParId(String id);
}
