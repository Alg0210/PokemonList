package com.WebApp.PokemonList.mappers;


import com.WebApp.PokemonList.dto.PokemonRequest;
import com.WebApp.PokemonList.dto.PokemonResponse;
import com.WebApp.PokemonList.model.PokemonEntry;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PokemonMapper {

    PokemonEntry toEntity(PokemonRequest dto);

    PokemonResponse toDto(PokemonEntry entity);

    List<PokemonResponse> toDtoList(List<PokemonEntry> entities);
}
