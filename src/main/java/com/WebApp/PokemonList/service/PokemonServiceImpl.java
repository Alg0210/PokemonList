package com.WebApp.PokemonList.service;

import com.WebApp.PokemonList.exception.NotFoundException;
import com.WebApp.PokemonList.model.PokeList;
import com.WebApp.PokemonList.model.PokemonEntry;
import com.WebApp.PokemonList.repository.PokeListRepository;
import com.WebApp.PokemonList.repository.PokemonEntryRepository;
import com.WebApp.PokemonList.service.external.PokeApiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonServiceImpl implements PokemonService{

    private final PokemonEntryRepository entryRepository;
    private final PokeListRepository listRepository;
    private final PokeApiService pokeApiService;

    public PokemonServiceImpl(PokemonEntryRepository entryRepository, PokeListRepository listRepository, PokeApiService pokeApiService) {
        this.entryRepository = entryRepository;
        this.listRepository = listRepository;
        this.pokeApiService = pokeApiService;
    }

    @Override
    public List<PokemonEntry> findByListId(Long listId) {
        if (!listRepository.existsById(listId)) {
            throw new NotFoundException("Pokelist not found with id " + listId);
        }
        return entryRepository.findByPokelistId(listId);
    }

    @Override
    public PokemonEntry findById(Long id) {
        return entryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pokemon entry not found with id " + id));
    }

    @Override
    public PokemonEntry addToList(Long listId, String species, String nickname) {
        PokeList list = listRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException("Pokelist not found with id" + listId));

        String sprite = pokeApiService.getSpriteUrl(species);
        PokemonEntry entry = PokemonEntry.builder()
                .species(species)
                .nickname(nickname)
                .spriteUrl(sprite)
                .pokelist(list)
                .build();

        return entryRepository.save(entry);
    }

    @Override
    public PokemonEntry update(Long id, String species, String nickname) {
        PokemonEntry entry = findById(id);

        if (!entry.getSpecies().equalsIgnoreCase(species)) {
            entry.setSpecies(species);
            entry.setSpriteUrl(pokeApiService.getSpriteUrl(species));
        }
        entry.setNickname(nickname);
        return entryRepository.save(entry);
    }

    @Override
    public void delete(Long id) {
        if (!entryRepository.existsById(id)) {
            throw new NotFoundException("Cannot delete; entry not found with id " + id);
        }
        entryRepository.deleteById(id);
    }
}
