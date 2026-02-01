package com.iut.banque.compteavecdecouvert.infra.rest;

import com.iut.banque.compteavecdecouvert.domain.catalog.CompteAvecDecouvertCatalog;
import com.iut.banque.compteavecdecouvert.domain.entity.CompteAvecDecouvert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comptes/avec-decouvert")
public class CompteAvecDecouvertController {

    private final CompteAvecDecouvertCatalog compteAvecDecouvertCatalog;

    @GetMapping("/a-decouvert/all")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<CompteAvecDecouvert>> getAllComptes() {
        List<CompteAvecDecouvert> comptes = compteAvecDecouvertCatalog.obtenirToutLesComptesADecouvert();
        return ResponseEntity.ok(comptes);
    }
}
