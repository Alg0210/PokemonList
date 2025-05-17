package com.WebApp.PokemonList.controller;


import com.WebApp.PokemonList.dto.PokemonRequest;
import com.WebApp.PokemonList.dto.PokemonResponse;
import com.WebApp.PokemonList.mappers.PokemonMapper;
import com.WebApp.PokemonList.model.PokemonEntry;
import com.WebApp.PokemonList.service.PokemonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokelists/{listId}/pokemon")
@CrossOrigin(origins = "http://localhost:5173")
public class PokemonController {

    private final PokemonService pokemonService;
    private final PokemonMapper mapper;

    public PokemonController(PokemonService pokemonService, PokemonMapper mapper) {
        this.pokemonService = pokemonService;
        this.mapper = mapper;
    }

    @GetMapping("/all-pokemon")
    public ResponseEntity<List<PokemonResponse>> getAllInList(@PathVariable Long listId){
        List<PokemonEntry> entities = pokemonService.findByListId(listId);

        List<PokemonResponse> dtos = mapper.toDtoList(entities);

        return ResponseEntity.ok(dtos);

    }

    @GetMapping("/{entryId}")
    public ResponseEntity<PokemonResponse> getById(@PathVariable Long listId, @PathVariable Long entryId){

        PokemonEntry entry = pokemonService.findById(listId, entryId);
        PokemonResponse dto = mapper.toDto(entry);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/add-pokemon")
    public ResponseEntity<PokemonResponse> addToList(@PathVariable Long listId,@Valid @RequestBody PokemonRequest entryReq){
        PokemonEntry entryEntity = mapper.toEntity(entryReq);
        PokemonEntry createdEntry = pokemonService.addToList(listId, entryEntity.getSpecies(), entryEntity.getNickname());
        PokemonResponse resp = mapper.toDto(createdEntry);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{entryId}")
    public ResponseEntity<PokemonResponse> update(@PathVariable Long listId, @PathVariable Long entryId, @Valid @RequestBody PokemonRequest entryReq){
        PokemonEntry toUpdate = mapper.toEntity(entryReq);
        PokemonEntry updatedEntry = pokemonService.updatePoke(listId, entryId, toUpdate.getSpecies(),toUpdate.getNickname());
        PokemonResponse resp = mapper.toDto(updatedEntry);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<Void> delete(@PathVariable Long listId, @PathVariable Long entryId){
        pokemonService.deletePoke(listId,entryId);
        return ResponseEntity.noContent().build();
    }

}
