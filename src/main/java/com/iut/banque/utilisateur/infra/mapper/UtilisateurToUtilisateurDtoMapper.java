package com.iut.banque.utilisateur.infra.mapper;

import com.iut.banque.model.UtilisateurDto;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface UtilisateurToUtilisateurDtoMapper extends Function<Utilisateur, UtilisateurDto> {
    @Override
    UtilisateurDto apply(Utilisateur utilisateur);
}
