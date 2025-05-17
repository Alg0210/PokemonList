package com.WebApp.PokemonList.mappers;

import com.WebApp.PokemonList.dto.ListRequest;
import com.WebApp.PokemonList.dto.ListResponse;
import com.WebApp.PokemonList.model.PokeList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ListMapper {

    @Mapping(target = "id", ignore = true)
    PokeList toEntity(ListRequest dto);

    ListResponse toDto(PokeList entity);
}
