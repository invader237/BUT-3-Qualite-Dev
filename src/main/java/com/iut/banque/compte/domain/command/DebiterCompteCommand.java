package com.iut.banque.compte.domain.command;

public record DebiterCompteCommand(
        String compteId,
        double montant
) {}