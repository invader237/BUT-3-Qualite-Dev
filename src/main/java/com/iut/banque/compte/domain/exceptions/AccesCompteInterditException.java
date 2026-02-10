package com.iut.banque.compte.domain.exceptions;

public class AccesCompteInterditException extends RuntimeException {
    public AccesCompteInterditException(String compteId, String userId) {
        super("Accès interdit au compte " + compteId + " pour l'utilisateur " + userId);
    }
}
