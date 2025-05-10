package com.WebApp.PokemonList.service;

import com.WebApp.PokemonList.model.PokeList;

import java.util.List;

public interface ListService {
    List<PokeList> findAll();
    PokeList findById(Long id);
    PokeList create(PokeList pokeList);
    PokeList update(Long id, PokeList pokeList);
    void delete(Long id);
}
