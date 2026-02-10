package com.iut.banque.client.infra.repository;

import com.iut.banque.client.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
}
