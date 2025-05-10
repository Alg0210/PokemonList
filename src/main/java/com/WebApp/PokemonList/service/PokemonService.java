package com.WebApp.PokemonList.service;

import com.WebApp.PokemonList.model.PokemonEntry;

import java.util.List;

public interface PokemonService {

    List<PokemonEntry> findByListId(Long listId);
    PokemonEntry findById(Long id);
    PokemonEntry addToList(Long listId, String species, String nickname);
    PokemonEntry update(Long id, String species, String nickname);
    void delete(Long id);
}
