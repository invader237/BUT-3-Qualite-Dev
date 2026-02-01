package com.iut.banque.shared.compte.infra.repository;

import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.shared.compte.domain.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, String> {
    List<Compte> findAllByOwner(Client owner);

    Optional<Compte> findByNumeroCompte(String id);
}
