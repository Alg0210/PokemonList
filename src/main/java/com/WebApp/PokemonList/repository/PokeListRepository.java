package com.WebApp.PokemonList.repository;

import com.WebApp.PokemonList.model.PokeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokeListRepository extends JpaRepository<PokeList,Long> {

    /**
     * Find a list by its exact name.
     */
    List<PokeList> findByName(String name);


}
