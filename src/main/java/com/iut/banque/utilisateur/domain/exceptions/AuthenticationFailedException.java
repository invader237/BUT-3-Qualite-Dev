package com.iut.banque.utilisateur.domain.exceptions;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException() {
        super("Identifiants invalides");
    }
}