package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.*
import javax.validation.constraints.NotBlank


data class EditEquipoDto(
    @get:NotBlank(message = "{equipo.nombre.blank}")
    var nombre: String,
    @get:NotBlank(message = "{equipo.liga.blank}")
    var liga: Liga,
    var listaPokemons: MutableList<Pokemon> = mutableListOf(),

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
