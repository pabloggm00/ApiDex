package com.salesianostriana.dam.ApiDex.repositories

import com.salesianostriana.dam.ApiDex.entities.ImagenPokemon
import org.springframework.data.jpa.repository.JpaRepository

interface ImagenPokemonRepository: JpaRepository<ImagenPokemon, Long> {

    abstract fun findByDeleteHash(deleteHash: String): ImagenPokemon
}