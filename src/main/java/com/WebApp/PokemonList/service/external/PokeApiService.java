package com.WebApp.PokemonList.service.external;

import com.WebApp.PokemonList.dto.GenerationSummary;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PokeApiService {
    private final WebClient webClient;

    public PokeApiService(WebClient.Builder webClientBuilder) {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                .build();

        this.webClient = webClientBuilder
                .baseUrl("https://pokeapi.co/api/v2")
                .exchangeStrategies(exchangeStrategies)
                .build();
    }

    @Cacheable("generations")
    public List<GenerationSummary> getGenerations() {
        return IntStream.rangeClosed(1, 9)
                .mapToObj(this::fetchGenerationSummary)
                .toList();
    }

    @Cacheable(value = "generationPokemon", key = "#generationId")
    public List<String> getSpeciesNamesByGeneration(int generationId) {
        JsonNode root = webClient
                .get()
                .uri("/generation/{id}", generationId)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        List<String> names = new ArrayList<>();
        for (JsonNode species : root.path("pokemon_species")) {
            names.add(species.path("name").asText());
        }
        return names;
    }

    @Cacheable("allPokemonSpecies")
    public List<String> getAllSpeciesNames() {
        List<String> names = new ArrayList<>();
        for (int generationId = 1; generationId <= 9; generationId++) {
            JsonNode root = webClient
                    .get()
                    .uri("/generation/{id}", generationId)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            for (JsonNode species : root.path("pokemon_species")) {
                names.add(species.path("name").asText());
            }
        }

        return names.stream().distinct().sorted().toList();
    }

    /**
     * Fetches the front-default sprite URL for a given Pokemon species.
     * Results are cached under "pokemonSprites" so repeat requests are fast.
     *
     * @param species the Pokemon name or ID
     * @return URL string of the front sprite, or empty string if missing
     */
    @Cacheable("pokemonSprites")
    public String getSpriteUrl(String species) {
        JsonNode root = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/pokemon/{name}")
                        .build(species.toLowerCase()))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        return root
                .path("sprites")
                .path("front_default")
                .asText("");
    }

    private GenerationSummary fetchGenerationSummary(int id) {
        JsonNode root = webClient
                .get()
                .uri("/generation/{id}", id)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .onErrorMap(WebClientResponseException.NotFound.class,
                        ex -> new IllegalArgumentException("Generation not found with id " + id))
                .block();

        return new GenerationSummary(
                root.path("id").asInt(),
                root.path("name").asText(),
                getEnglishName(root.path("names")),
                root.path("main_region").path("name").asText("")
        );
    }

    private String getEnglishName(JsonNode names) {
        for (JsonNode entry : names) {
            if ("en".equals(entry.path("language").path("name").asText())) {
                return entry.path("name").asText();
            }
        }
        return "";
    }
}
