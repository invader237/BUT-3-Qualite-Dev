package com.iut.banque.utilisateur.domain.command;

import com.iut.banque.utilisateur.domain.entity.Utilisateur;

public record ChangePasswordCommand(Utilisateur utilisateur, String oldPassword, String newPassword) {}
