package com.WebApp.PokemonList.controller;

import com.WebApp.PokemonList.dto.GenerationSummary;
import com.WebApp.PokemonList.dto.PokemonBrowseItem;
import com.WebApp.PokemonList.service.PokemonCatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon-catalog")
public class PokemonCatalogController {

    private final PokemonCatalogService catalogService;

    public PokemonCatalogController(PokemonCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/generations")
    public ResponseEntity<List<GenerationSummary>> getGenerations() {
        return ResponseEntity.ok(catalogService.getGenerations());
    }

    @GetMapping("/generations/{id}/pokemon")
    public ResponseEntity<List<PokemonBrowseItem>> browseGeneration(@PathVariable int id) {
        return ResponseEntity.ok(catalogService.browseGeneration(id));
    }

    @GetMapping("/pokemon/search")
    public ResponseEntity<List<PokemonBrowseItem>> searchPokemon(@RequestParam String query) {
        return ResponseEntity.ok(catalogService.searchPokemon(query));
    }
}
