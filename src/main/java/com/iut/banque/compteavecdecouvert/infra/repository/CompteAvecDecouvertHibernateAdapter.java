package com.iut.banque.compteavecdecouvert.infra.repository;

import com.iut.banque.compteavecdecouvert.domain.catalog.CompteAvecDecouvertCatalog;
import com.iut.banque.compteavecdecouvert.domain.entity.CompteAvecDecouvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompteAvecDecouvertHibernateAdapter implements CompteAvecDecouvertCatalog {

    private final CompteAvecDecouvertRepository compteAvecDecouvertRepository;

    @Override
    public List<CompteAvecDecouvert> obtenirToutLesComptesADecouvert() {
        return compteAvecDecouvertRepository.findCompteAvecDecouvertBySoldeLessThan(0.0);
    }
}
