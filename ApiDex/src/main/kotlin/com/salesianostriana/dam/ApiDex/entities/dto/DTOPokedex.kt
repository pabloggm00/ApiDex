package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.Pokedex

data class GetPokedexDto(
    var generacion: Int,
    var nombre: String
    )

fun Pokedex.toGetPokedexDto(): GetPokedexDto{
    return GetPokedexDto(generacion, nombre)
}