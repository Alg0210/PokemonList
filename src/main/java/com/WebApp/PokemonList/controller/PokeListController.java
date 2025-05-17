package com.WebApp.PokemonList.controller;


import com.WebApp.PokemonList.dto.ListRequest;
import com.WebApp.PokemonList.dto.ListResponse;
import com.WebApp.PokemonList.mappers.ListMapper;
import com.WebApp.PokemonList.model.PokeList;
import com.WebApp.PokemonList.service.ListService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pokelists")
@CrossOrigin(origins = "http://localhost:5173")
public class PokeListController {

    private final ListService pokeListService;
    private final ListMapper mapper;

    public PokeListController(ListService pokeListService, ListMapper mapper) {
        this.pokeListService = pokeListService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ListResponse>> getAll(){
        List<PokeList> entities = pokeListService.findAll();
        List<ListResponse> dtos = entities.stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListResponse> getById(@PathVariable Long id){
        PokeList entity = pokeListService.findById(id);
        ListResponse dto = mapper.toDto(entity);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/new-list")
    public ResponseEntity<ListResponse> create(@Valid @RequestBody ListRequest ListReq){
        PokeList toSave = mapper.toEntity(ListReq);
        PokeList saved = pokeListService.create(toSave);
        ListResponse resp = mapper.toDto(saved);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListResponse> update(@PathVariable Long id, @Valid @RequestBody ListRequest ListReq){
        PokeList entity = mapper.toEntity(ListReq);
        PokeList updatedEntity = pokeListService.update(id,entity);
        ListResponse resp = mapper.toDto(updatedEntity);


        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        pokeListService.delete(id);
        return ResponseEntity.noContent().build();

    }


}
