package com.WebApp.PokemonList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PokemonResponse {

    private Long id;
    private String species;
    private String nickname;
    private String spriteUrl;
}
