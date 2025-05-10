package com.WebApp.PokemonList.service.external;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PokeApiService {
    private final WebClient webClient;

    public PokeApiService(WebClient.Builder webClientBuilder) {
        // Base URL for the PokéAPI v2 endpoints
        this.webClient = webClientBuilder
                .baseUrl("https://pokeapi.co/api/v2")
                .build();
    }


    /**
     * Fetches the front-default sprite URL for a given Pokémon species.
     * Results are cached under "pokemonSprites" so repeat requests are fast
     * and don’t repeatedly hit the remote API.
     *
     * @param species the Pokémon name or ID (case‑insensitive)
     * @return URL string of the front sprite, or empty string if missing
     */
    @Cacheable("pokemonSprites")
    public String getSpriteUrl(String species) {
        // Call GET /pokemon/{name}
        JsonNode root = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/pokemon/{name}")
                        .build(species.toLowerCase()))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        // Navigate the JSON to sprites.front_default
        return root
                .path("sprites")
                .path("front_default")
                .asText("");
    }
}
