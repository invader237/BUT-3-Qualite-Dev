package com.iut.banque.utilisateur.domain.exceptions;

public class DonneesUtilisateurInvalidesException extends RuntimeException {
    public DonneesUtilisateurInvalidesException(String userId) {
        super("Données utilisateur invalides : " + userId);
    }
}
