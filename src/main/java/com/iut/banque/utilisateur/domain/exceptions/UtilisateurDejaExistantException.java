package com.iut.banque.utilisateur.domain.exceptions;

public class UtilisateurDejaExistantException extends RuntimeException {
    public UtilisateurDejaExistantException(String userId) {
        super("Utilisateur déjà existant avec l'ID : " + userId);
    }
}
