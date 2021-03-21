package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.*


data class EditEquipoDto(
    var nombre: String,
    var listaPokemon: MutableList<Pokemon>,
    var liga: Liga
)

data class GetEquipoDto(
    var nombre: String,
    var liga: Liga
)

fun Equipo.toGetEquipoDto(): GetEquipoDto = GetEquipoDto(nombre,liga)

data class GetEquipoDetalleDto(
    var nombre: String,
    var listaPokemon: List<GetPokemonEquipoDto>
)


fun Equipo.toGetEquipoDetalleDto(): GetEquipoDetalleDto {

    var listaPokemon: MutableList<GetPokemonEquipoDto> = mutableListOf()
    listaPokemon.forEach { i ->
        listaPokemon.add(GetPokemonEquipoDto(i!!.id, "${i.imagen}", i.pC ))
    }



    return GetEquipoDetalleDto(
        nombre,
        listaPokemon
    )

}
