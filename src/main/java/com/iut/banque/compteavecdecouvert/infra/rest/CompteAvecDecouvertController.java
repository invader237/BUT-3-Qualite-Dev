package com.iut.banque.compteavecdecouvert.infra.rest;

import com.iut.banque.api.ComptesAvecDecouvertApi;
import com.iut.banque.compte.infra.mapper.CompteToCompteDtoMapper;
import com.iut.banque.compteavecdecouvert.domain.catalog.CompteAvecDecouvertCatalog;
import com.iut.banque.compteavecdecouvert.domain.entity.CompteAvecDecouvert;
import com.iut.banque.model.CompteDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompteAvecDecouvertController implements ComptesAvecDecouvertApi {

    private final CompteAvecDecouvertCatalog compteAvecDecouvertCatalog;
    private final CompteToCompteDtoMapper compteToCompteDtoMapper;

    private static final Logger log = LoggerFactory.getLogger(CompteAvecDecouvertController.class);

    @Override
    public List<CompteDto> getAllComptes() {

        log.info("Récupération de tous les comptes à découvert");

        List<CompteAvecDecouvert> comptes = compteAvecDecouvertCatalog.obtenirToutLesComptesADecouvert();

        log.info("Récupération réussie de {} comptes à découvert", comptes.size());

        return comptes.stream()
                .map(compteToCompteDtoMapper).toList();
    }
}
