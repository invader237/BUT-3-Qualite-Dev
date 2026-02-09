package com.iut.banque.utilisateur.infra.mapper;

import com.iut.banque.model.CreateUtilisateurRequest;
import com.iut.banque.utilisateur.domain.command.RegisterUserCommand;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface CreateUtilisateurRequestToRegisterUserCommand extends Function<CreateUtilisateurRequest, RegisterUserCommand> {
    @Override
    RegisterUserCommand apply(CreateUtilisateurRequest createUtilisateurRequest);
}
