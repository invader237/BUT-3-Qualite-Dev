package com.iut.banque.utilisateur.infra.mapper;

import com.iut.banque.model.LoginRequest;
import com.iut.banque.utilisateur.domain.command.LoginCommand;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface LoginRequestToLoginCommandMapper extends Function<LoginRequest, LoginCommand> {
    @Override
    LoginCommand apply(LoginRequest loginRequest);
}
