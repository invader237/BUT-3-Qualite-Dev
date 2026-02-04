package com.iut.banque.compte.domain.command;

public record CrediterCompteCommand(
        String compteId,
        double montant
) {}