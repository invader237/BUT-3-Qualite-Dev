package com.iut.banque.utilisateur.domain.exceptions;

public class ModificationDeMotDePasseException extends RuntimeException {
    public ModificationDeMotDePasseException(String message) {
        super("Erreur lors de la modification du mot de passe : " + message);
    }
}
