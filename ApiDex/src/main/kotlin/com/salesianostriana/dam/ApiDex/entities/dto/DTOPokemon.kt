package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.*


data class EditPokemonDto(
    var nombre: String,
    var estrellas: Int,
    var ataqueRapido: String,
    var ataqueCargado: String,
    var pc: Int
)

data class GetPokemonDto(
    var id: Long?,
    var nombre: String,
    var estrellas: Int,
    var ataqueRapido: String,
    var ataqueCargado: String,
    var pc: Int,
    var fav: Boolean
)

data class GetPokemoDetalleDto(
    var id: Long?,
    var nombre: String,
    var estrellas: Int,
    var ataqueRapido: String,
    var ataqueCargado: String,
    var pc: Int,
    var imagen: String?,
    var generacion: String,
    var primerTipo: String,
    var segundoTipo: String?,
    var fav: Boolean
)

fun Pokemon.toGetPokemonDto(usuario: Usuario): GetPokemonDto{

    var favorito = false

    if (usuario != null){
        for(pokemon in usuario.pokemonsFavs){
            if(pokemon.id == id){
                favorito = true
            }
        }
    }

    return GetPokemonDto(
            id,
            nombre,
            estrellas,
            ataqueRapido,
            ataqueCargado,
            pc,
            favorito
        )
}

fun Pokemon.toGetPokemonDetalleDto(usuario: Usuario): GetPokemoDetalleDto {
    var favorito = false

    if (usuario != null){
        for(pokemon in usuario.pokemonsFavs){
            if(pokemon.id == id){
                favorito = true
            }
        }
    }

    var url: String = "http://10.0.2.2:9000/files/"

    lateinit var imagenPokemon: ImagenPokemon

    if (imagen != null) {
        return GetPokemoDetalleDto(
            id,
            nombre,
            estrellas,
            ataqueRapido,
            ataqueCargado,
            pc,
            "${url}${imagenPokemon.dataId}",
            generacion.nombre,
            primerTipo.nombreTipo,
            segundoTipo?.nombreTipo,
            favorito
        )
    }else{
        return GetPokemoDetalleDto(
            id,
            nombre,
            estrellas,
            ataqueRapido,
            ataqueCargado,
            pc,
            "",
            generacion.nombre,
            primerTipo.nombreTipo,
            segundoTipo?.nombreTipo,
            favorito
        )
    }
}

