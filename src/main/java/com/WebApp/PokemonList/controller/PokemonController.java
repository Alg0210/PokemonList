package com.WebApp.PokemonList.controller;


import com.WebApp.PokemonList.model.PokemonEntry;
import com.WebApp.PokemonList.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokelists/{listId}/pokemon")
@CrossOrigin(origins = "http://localhost:5173")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public List<PokemonEntry> getAllInList(@PathVariable Long listId){
        return pokemonService.findByListId(listId);
    }

    @GetMapping("/{entryId}")
    public ResponseEntity<PokemonEntry> getById(@PathVariable Long listId, @PathVariable Long entryId){
        PokemonEntry entry = pokemonService.findById(listId, entryId);
        return ResponseEntity.ok(entry);
    }

    @PostMapping
    public ResponseEntity<PokemonEntry> addToList(@PathVariable Long listId, @RequestBody PokemonEntry entry){
        PokemonEntry createdEntry = pokemonService.addToList(listId, entry.getSpecies(), entry.getNickname());
        return ResponseEntity.ok(createdEntry);
    }

    @PutMapping("/{entryId}")
    public ResponseEntity<PokemonEntry> update(@PathVariable Long listId, @PathVariable Long entryId, @RequestBody PokemonEntry entry){
        PokemonEntry updatedEntry = pokemonService.updatePoke(listId, entryId, entry.getSpecies(),entry.getNickname());
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<Void> delete(@PathVariable Long listId, @PathVariable Long entryId){
        pokemonService.deletePoke(listId,entryId);
        return ResponseEntity.noContent().build();
    }

}
