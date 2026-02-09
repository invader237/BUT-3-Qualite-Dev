package com.iut.banque.utilisateur.domain.usecase;

import com.iut.banque.model.TokenResponse;
import com.iut.banque.shared.auth.domain.service.AuthService;
import com.iut.banque.utilisateur.domain.command.LoginCommand;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthService authService;

    public TokenResponse execute(LoginCommand command) {
        Utilisateur utilisateur = authService.authenticateUser(command.userId(), command.password());

        String token = authService.generateAuthToken(utilisateur);

        return new TokenResponse(token);
    }
}