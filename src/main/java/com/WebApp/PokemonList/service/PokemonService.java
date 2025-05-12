package com.WebApp.PokemonList.service;

import com.WebApp.PokemonList.model.PokemonEntry;

import java.util.List;

public interface PokemonService {

    List<PokemonEntry> findByListId(Long listId);
    PokemonEntry findById(Long listId, Long entryId);
    PokemonEntry addToList(Long listId, String species, String nickname);
    PokemonEntry updatePoke(Long listId, Long entryId, String species, String nickname);
    void deletePoke(Long id, Long entryId);
}
