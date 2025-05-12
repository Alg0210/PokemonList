package com.WebApp.PokemonList.service;

import com.WebApp.PokemonList.exception.NotFoundException;
import com.WebApp.PokemonList.model.PokeList;
import com.WebApp.PokemonList.model.PokemonEntry;
import com.WebApp.PokemonList.repository.PokeListRepository;
import com.WebApp.PokemonList.repository.PokemonEntryRepository;
import com.WebApp.PokemonList.service.external.PokeApiService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public PokemonEntry findById(Long listId, Long entryId) {
        return entryRepository.findByIdAndPokelistId(listId,entryId)
                .orElseThrow(() -> new NotFoundException("Pokemon entry not found with id " + entryId + " in list " + listId));
    }

    @Override
    public PokemonEntry addToList(Long listId, String species, String nickname) {
        PokeList list = listRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException("Pokelist not found with id" + listId));


        boolean exists = entryRepository.findByPokelistId(listId)
                .stream()
                .anyMatch(e -> e.getSpecies().equalsIgnoreCase(species));
        if (exists) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Species '" + species + "' already exists in list id " + listId
            );
        }

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
    public PokemonEntry updatePoke(Long listId, Long entryId, String species, String nickname) {
        PokemonEntry entry = findById(listId, entryId);

        if (!entry.getSpecies().equalsIgnoreCase(species)) {
            entry.setSpecies(species);
            entry.setSpriteUrl(pokeApiService.getSpriteUrl(species));
        }
        entry.setNickname(nickname);
        return entryRepository.save(entry);
    }

    @Override
    public void deletePoke(Long listId, Long entryId) {
        PokemonEntry entry = entryRepository
                .findByIdAndPokelistId(entryId, listId)
                .orElseThrow(() -> new NotFoundException(
                        "No Pokemon entry with id " + entryId +
                                " in list " + listId));

        entryRepository.delete(entry);
    }
}
