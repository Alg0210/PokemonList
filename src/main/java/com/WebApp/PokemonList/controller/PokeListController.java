package com.WebApp.PokemonList.controller;


import com.WebApp.PokemonList.model.PokeList;
import com.WebApp.PokemonList.service.ListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokelists")
@CrossOrigin(origins = "http://localhost:5173")
public class PokeListController {

    private final ListService pokeListService;

    public PokeListController(ListService pokeListService) {
        this.pokeListService = pokeListService;
    }

    @GetMapping
    public List<PokeList> getAll(){
        return pokeListService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokeList> getById(@PathVariable Long id){
        PokeList list = pokeListService.findById(id);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<PokeList> create(@RequestBody PokeList pokeList){
        PokeList createdList = pokeListService.create(pokeList);
        return ResponseEntity.ok(createdList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PokeList> update(@PathVariable Long id, @RequestBody PokeList pokeList){

        PokeList updatedList = pokeListService.update(id,pokeList);
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        pokeListService.delete(id);
        return ResponseEntity.noContent().build();

    }


}
