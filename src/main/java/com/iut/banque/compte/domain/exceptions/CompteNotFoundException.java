package com.iut.banque.compte.domain.exceptions;

public class CompteNotFoundException extends RuntimeException {

    public CompteNotFoundException(String compteId) {
        super("Compte introuvable : " + compteId);
    }
}
