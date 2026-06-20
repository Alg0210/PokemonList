package com.WebApp.PokemonList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerationSummary {

    private int id;
    private String name;
    private String displayName;
    private String region;
}
