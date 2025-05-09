package com.WebApp.PokemonList.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Pokemon species must not be blank")
    private String species;

    private String nickname;

    private String spriteUrl;   // URL fetched from PokeAPI

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokelist_id")
    private PokeList pokelist;
}

