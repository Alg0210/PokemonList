package com.WebApp.PokemonList.service;

import com.WebApp.PokemonList.dto.GenerationSummary;
import com.WebApp.PokemonList.dto.PokemonBrowseItem;
import com.WebApp.PokemonList.exception.NotFoundException;
import com.WebApp.PokemonList.service.external.PokeApiService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PokemonCatalogService {

    private static final int SEARCH_RESULT_LIMIT = 20;

    private final PokeApiService pokeApiService;

    public PokemonCatalogService(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    public List<GenerationSummary> getGenerations() {
        return pokeApiService.getGenerations();
    }

    public List<PokemonBrowseItem> browseGeneration(int generationId) {
        validateGenerationId(generationId);

        return pokeApiService.getSpeciesNamesByGeneration(generationId).stream()
                .sorted(Comparator.naturalOrder())
                .map(this::toBrowseItem)
                .toList();
    }

    public List<PokemonBrowseItem> searchPokemon(String query) {
        String normalizedQuery = query == null ? "" : query.trim().toLowerCase();
        if (normalizedQuery.isEmpty()) {
            return List.of();
        }

        return pokeApiService.getAllSpeciesNames().stream()
                .filter(name -> name.contains(normalizedQuery))
                .sorted(Comparator.naturalOrder())
                .limit(SEARCH_RESULT_LIMIT)
                .map(this::toBrowseItem)
                .toList();
    }

    private PokemonBrowseItem toBrowseItem(String name) {
        return new PokemonBrowseItem(name, pokeApiService.getSpriteUrl(name));
    }

    private void validateGenerationId(int generationId) {
        if (generationId < 1 || generationId > 9) {
            throw new NotFoundException("Generation not found with id " + generationId);
        }
    }
}
