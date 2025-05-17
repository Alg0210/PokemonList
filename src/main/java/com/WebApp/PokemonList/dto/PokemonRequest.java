package com.WebApp.PokemonList.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PokemonRequest {

    @NotBlank(message = "Pokemon species must not be blank")
    private String species;
    private String nickname;


}
