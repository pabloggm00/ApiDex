package com.salesianostriana.dam.ApiDex.repositories

import com.salesianostriana.dam.ApiDex.entities.Pokemon
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PokemonRepository: JpaRepository<Pokemon, Long> {
}