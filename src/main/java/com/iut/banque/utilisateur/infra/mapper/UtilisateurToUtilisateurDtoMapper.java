package com.iut.banque.utilisateur.infra.mapper;

import com.iut.banque.model.UtilisateurDto;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import jakarta.persistence.DiscriminatorValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface UtilisateurToUtilisateurDtoMapper extends Function<Utilisateur, UtilisateurDto> {
    @Override
    @Mapping(target = "type", expression = "java(resolveType(utilisateur))")
    UtilisateurDto apply(Utilisateur utilisateur);

    default String resolveType(Utilisateur utilisateur) {
        DiscriminatorValue dv = utilisateur.getClass().getAnnotation(DiscriminatorValue.class);
        return dv != null ? dv.value() : null;
    }
}
