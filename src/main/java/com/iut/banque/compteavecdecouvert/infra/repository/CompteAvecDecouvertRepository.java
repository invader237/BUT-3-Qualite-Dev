package com.iut.banque.compteavecdecouvert.infra.repository;

import com.iut.banque.compteavecdecouvert.domain.entity.CompteAvecDecouvert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompteAvecDecouvertRepository extends JpaRepository<CompteAvecDecouvert, String> {
    List<CompteAvecDecouvert> findCompteAvecDecouvertBySoldeLessThan(double solde);
}
