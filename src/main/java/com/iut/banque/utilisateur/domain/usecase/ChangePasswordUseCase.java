package com.iut.banque.utilisateur.domain.usecase;

import com.iut.banque.shared.auth.domain.service.AuthService;
import com.iut.banque.utilisateur.domain.command.ChangePasswordCommand;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import com.iut.banque.utilisateur.domain.exceptions.AuthenticationFailedException;
import com.iut.banque.utilisateur.domain.exceptions.ModificationDeMotDePasseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final AuthService authService;

    public void handle(ChangePasswordCommand changePasswordCommand) {

        Utilisateur authenticatedUser = authService.authenticateUser(changePasswordCommand.utilisateur().getUserId(), changePasswordCommand.oldPassword());

        if (!authenticatedUser.getUserId().equals(changePasswordCommand.utilisateur().getUserId())) {
            throw new AuthenticationFailedException();
        }

        authService.changePassword(authenticatedUser, changePasswordCommand.newPassword());

    }

}
