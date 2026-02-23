package com.iut.banque.compte.infra.mapper;

import com.iut.banque.compte.domain.entity.Compte;
import com.iut.banque.compteavecdecouvert.domain.entity.CompteAvecDecouvert;
import com.iut.banque.model.CompteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface CompteToCompteDtoMapper extends Function<Compte, CompteDto> {
    @Override
    @Mapping(target = "id", source = "numeroCompte")
    @Mapping(target = "clientId", source = "owner.userId")
    @Mapping(target = "decouvertAutorise", expression = "java(resolveDecouvert(compte))")
    CompteDto apply(Compte compte);

    default Double resolveDecouvert(Compte compte) {
        if (compte instanceof CompteAvecDecouvert cad) {
            return cad.getDecouvertAutorise();
        }
        return 0.0;
    }
}
