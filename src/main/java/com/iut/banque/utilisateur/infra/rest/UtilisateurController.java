package com.iut.banque.utilisateur.infra.rest;

import com.iut.banque.api.UtilisateursApi;
import com.iut.banque.model.*;
import com.iut.banque.shared.auth.domain.service.AuthService;
import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compte.domain.usecase.CompteUseCase;
import com.iut.banque.shared.auth.infra.GetUser;
import com.iut.banque.utilisateur.domain.catalog.UtilisateurCatalog;
import com.iut.banque.utilisateur.domain.command.ChangePasswordCommand;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import com.iut.banque.utilisateur.domain.usecase.ChangePasswordUseCase;
import com.iut.banque.utilisateur.domain.usecase.LoginUseCase;
import com.iut.banque.utilisateur.infra.mapper.CreateUtilisateurRequestToRegisterUserCommand;
import com.iut.banque.utilisateur.infra.mapper.LoginRequestToLoginCommandMapper;
import com.iut.banque.utilisateur.infra.mapper.UtilisateurToUtilisateurDtoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UtilisateurController implements UtilisateursApi {

    private final UtilisateurCatalog utilisateurCatalog;
    private final CompteUseCase compteUseCase;
    private final CompteCatalog compteCatalog;
    private final AuthService authService;
    private final LoginUseCase loginUseCase;
    private final UtilisateurToUtilisateurDtoMapper utilisateurToUtilisateurDtoMapper;
    private final CreateUtilisateurRequestToRegisterUserCommand createUtilisateurRequestToRegisterUserCommand;
    private final LoginRequestToLoginCommandMapper loginRequestToLoginCommandMapper;

    private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);
    private final GetUser getUser;
    private final ChangePasswordUseCase changePasswordUseCase;

    @Override
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_MANAGER')")
    public UtilisateurDto me() {
        log.info("Récupération des informations de l'utilisateur connecté id: {}", getUser.apply().getUserId());
        return utilisateurToUtilisateurDtoMapper.apply(getUser.apply());
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_MANAGER')")
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Utilisateur utilisateur = getUser.apply();

        log.info("Changement de mot de passe demandé pour l'utilisateur id: {}", utilisateur.getUserId());

        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand(
                utilisateur,
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword()
        );
        changePasswordUseCase.handle(changePasswordCommand);

        log.info("Changement de mot de passe réussi pour l'utilisateur id: {}", utilisateur.getUserId());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public UtilisateurDto createUtilisateur(CreateUtilisateurRequest createUtilisateurRequest) {

        log.info("Création d'un nouvel utilisateur avec l'id: {}", createUtilisateurRequest.getUserId());

        authService.registerUser(createUtilisateurRequestToRegisterUserCommand.apply(createUtilisateurRequest));

        log.info("Utilisateur créé avec succès avec l'id: {}", createUtilisateurRequest.getUserId());

        return utilisateurToUtilisateurDtoMapper.apply(utilisateurCatalog.obtenirUtilisateurParUtilisateurId(createUtilisateurRequest.getUserId()));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public UtilisateurDto getUtilisateur(String userId) {

        log.info("Récupération de l'utilisateur avec l'id: {}", userId);

        Utilisateur utilisateur = utilisateurCatalog.obtenirUtilisateurParUtilisateurId(userId);

        log.info("Récupération de l'utilisateur réussie avec l'id: {}", userId);

        return utilisateurToUtilisateurDtoMapper.apply(utilisateur);
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {

        log.info("Login attempt for userId={}", loginRequest.getUserId());

        return loginUseCase.execute(loginRequestToLoginCommandMapper.apply(loginRequest));
    }

    @Override
    public void logout() {

        log.info("Logout request received for userId={}", getUser.apply().getUserId());

        authService.logout();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public UtilisateurDto updateUtilisateur(String userId, UpdateUtilisateurRequest updateUtilisateurRequest) {
        return null;
    }
}
