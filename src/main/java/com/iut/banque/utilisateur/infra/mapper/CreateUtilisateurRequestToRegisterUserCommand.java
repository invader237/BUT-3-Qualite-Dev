package com.iut.banque.utilisateur.infra.mapper;

import com.iut.banque.model.CreateUtilisateurRequest;
import com.iut.banque.utilisateur.domain.command.RegisterUserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface CreateUtilisateurRequestToRegisterUserCommand extends Function<CreateUtilisateurRequest, RegisterUserCommand> {
    @Override
    @Mapping(target = "password", source = "createUtilisateurRequest.userPwd")
    RegisterUserCommand apply(CreateUtilisateurRequest createUtilisateurRequest);
}
