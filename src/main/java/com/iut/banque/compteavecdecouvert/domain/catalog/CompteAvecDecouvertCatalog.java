package com.iut.banque.compteavecdecouvert.domain.catalog;

import com.iut.banque.compteavecdecouvert.domain.entity.CompteAvecDecouvert;

import java.util.List;

public interface CompteAvecDecouvertCatalog {
    List<CompteAvecDecouvert> obtenirToutLesComptesADecouvert();
}
