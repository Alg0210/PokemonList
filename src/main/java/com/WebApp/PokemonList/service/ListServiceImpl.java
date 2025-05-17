package com.WebApp.PokemonList.service;


import com.WebApp.PokemonList.exception.NotFoundException;
import com.WebApp.PokemonList.model.PokeList;
import com.WebApp.PokemonList.repository.PokeListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ListServiceImpl implements ListService {


    private final PokeListRepository repository;

    public ListServiceImpl(PokeListRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<PokeList> findAll() {
        return repository.findAll();
    }

    @Override
    public PokeList findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pokelist not found with id " + id));
    }

    @Override
    public PokeList create(PokeList pokeList) {

        if (repository.existsByNameIgnoreCase(pokeList.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Pokelist with name '" + pokeList.getName() + "' already exists"
            );
        }
        return repository.save(pokeList);
    }

    @Override
    public PokeList update(Long id, PokeList updated) {
        PokeList existing = findById(id);

        if(repository.existsByNameIgnoreCase(updated.getName()) && !updated.getName().equalsIgnoreCase(existing.getName())) {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Pokelist with this name already exists");
        }
        existing.setName(updated.getName());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Cannot delete; pokelist not found with id " + id);
        }
        repository.deleteById(id);
    }
}
