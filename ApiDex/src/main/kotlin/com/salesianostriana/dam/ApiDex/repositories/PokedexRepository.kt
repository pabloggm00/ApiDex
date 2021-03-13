package com.salesianostriana.dam.ApiDex.repositories

import com.salesianostriana.dam.ApiDex.entities.Pokedex
import org.springframework.data.jpa.repository.JpaRepository

interface PokedexRepository: JpaRepository<Pokedex, Long> {
}