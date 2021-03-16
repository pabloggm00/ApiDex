package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.Equipo
import com.salesianostriana.dam.ApiDex.entities.ImagenPokemon


data class GetEquipoDto(
    var nombre: String,
    var liga: String
)

fun Equipo.toGetEquipoDto(): GetEquipoDto = GetEquipoDto(nombre,liga.nombre)

data class GetEquipoDetalleDto(
    var nombre: String,
    var listaPokemon: List<GetPokemonEquipoDto>,
    var totalPC: Int
)

/*
fun Equipo.toGetEquipoDetalleDto(): GetEquipoDetalleDto {

    var listaPokemon: MutableList<GetPokemonEquipoDto> = mutableListOf()
    listaPokemon.forEach { i ->
        listaPokemon.add(GetPokemonEquipoDto(i!!.id, "${i.imagen}", i.pC ))
    }

    return GetEquipoDetalleDto(
        nombre,
        listaPokemon,
        totalPC
    )

}*/
