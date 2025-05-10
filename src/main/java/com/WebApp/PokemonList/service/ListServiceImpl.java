package com.WebApp.PokemonList.service;


import com.WebApp.PokemonList.exception.NotFoundException;
import com.WebApp.PokemonList.model.PokeList;
import com.WebApp.PokemonList.repository.PokeListRepository;
import org.springframework.stereotype.Service;

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
        return repository.save(pokeList);
    }

    @Override
    public PokeList update(Long id, PokeList updated) {
        PokeList existing = findById(id);
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
