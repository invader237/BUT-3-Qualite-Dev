package com.iut.banque.compte.infra.mapper;

import com.iut.banque.compte.domain.entity.Compte;
import com.iut.banque.model.CompteDto;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface CompteToCompteDtoMapper extends Function<Compte, CompteDto> {
    @Override
    CompteDto apply(Compte compte);
}
