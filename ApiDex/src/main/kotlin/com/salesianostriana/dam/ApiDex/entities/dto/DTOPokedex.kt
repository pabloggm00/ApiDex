package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.Pokedex
import com.salesianostriana.dam.ApiDex.entities.Pokemon

data class GetPokedexDto(
    var generacion: Int,
    var nombre: String,
    var listaPokemon: MutableList<Pokemon>
    )

fun Pokedex.toGetPokedexDto(): GetPokedexDto{
    return GetPokedexDto(generacion, nombre, listaPokemon)
}