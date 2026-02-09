package com.iut.banque.utilisateur.domain.command;

public record RegisterUserCommand (
    String userId,
    String password,
    String nom,
    String prenom,
    String adresse,
    boolean male,
    boolean isClient,
    String numClient
) {}