package com.WebApp.PokemonList.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokeList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "List name must not be blank")
    private String name;

    @OneToMany(
            mappedBy = "pokelist",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PokemonEntry> entries;
}

