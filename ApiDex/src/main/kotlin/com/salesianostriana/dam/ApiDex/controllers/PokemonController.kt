package com.salesianostriana.dam.ApiDex.controllers

import com.salesianostriana.dam.ApiDex.services.ImagenPokemonService
import com.salesianostriana.dam.ApiDex.services.PokemonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon")
class PokemonController {

    @Autowired
    lateinit var pokemonService: PokemonService

    @Autowired
    lateinit var imagenService: ImagenPokemonService

    @GetMapping
    fun getAllPokemon(
        @RequestParam(name="tipo", required = false, defaultValue = "Todos") tipo: String,
        @RequestParam(name="generacion", required = false, defaultValue = "Todas") generacion: String
    ){
        
    }
}