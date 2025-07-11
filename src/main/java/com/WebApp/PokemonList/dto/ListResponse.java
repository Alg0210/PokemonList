package com.WebApp.PokemonList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse {

    private Long id;
    private String name;
    private List<PokemonResponse> entries;
}
