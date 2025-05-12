package com.WebApp.PokemonList.repository;


import com.WebApp.PokemonList.model.PokemonEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonEntryRepository extends JpaRepository<PokemonEntry,Long> {
    Optional<PokemonEntry> findByIdAndPokelistId(Long entryId, Long listId);
    /**
     * Fetch all Pokémon entries belonging to a specific list.
     */
    List<PokemonEntry> findByPokelistId(Long pokeListId);

}
