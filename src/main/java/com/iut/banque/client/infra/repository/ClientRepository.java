package com.iut.banque.client.infra.repository;

import com.iut.banque.client.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
