package com.iut.banque.utilisateur.infra.repository;

import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.gestionnaire.domain.entity.Gestionnaire;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {

    Optional<Utilisateur> getUtilisateurByUserId(String id);

    @Query("SELECT c FROM Client c")
    List<Client> findAllClients();

    @Query("SELECT g FROM Gestionnaire g")
    List<Gestionnaire> findAllGestionnaires();

    boolean existsByUserId(String username);
}
