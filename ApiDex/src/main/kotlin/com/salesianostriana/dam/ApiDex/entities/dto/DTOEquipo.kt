package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.*


data class EditEquipoDto(
    var nombre: String,
    var listaPokemons: MutableList<Pokemon> = mutableListOf(),
    var liga: Liga
)

data class GetEquipoDto(
    var id: Long?,
    var nombre: String,
    var liga: Liga
)

fun Equipo.toGetEquipoDto(): GetEquipoDto = GetEquipoDto(id,nombre,liga)

data class GetEquipoDetalleDto(
    var id: Long?,
    var nombre: String,
    var listaPokemons: List<GetPokemonEquipoDto>
)


fun Equipo.toGetEquipoDetalleDto(): GetEquipoDetalleDto {

    val url: String = "http://10.0.2.2:9000/files/"

    var listaPokemons: MutableList<GetPokemonEquipoDto> = mutableListOf()
    listaPokemon.forEach { i ->
        listaPokemons.add(GetPokemonEquipoDto(i.id, "${url}${i.imagen!!.dataId}", i.pC ))
        println(i.nombre)
    }

    println(listaPokemons)
    println(listaPokemon)


    return GetEquipoDetalleDto(
        id,
        nombre,
        listaPokemons
    )

}
