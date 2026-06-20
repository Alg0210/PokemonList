package com.WebApp.PokemonList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PokemonListApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokemonListApplication.class, args);
	}

}
