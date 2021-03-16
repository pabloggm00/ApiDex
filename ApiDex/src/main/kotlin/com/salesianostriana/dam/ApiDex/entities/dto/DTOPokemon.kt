package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.*


data class EditPokemonDto(
    var estrellas: Int,
    var ataqueRapido: String,
    var ataqueCargado: String,
    var pC: Int
)

data class GetPokemonPokedexDto(
    var id: Long?,
    var idPokedex: String,
    var nombre: String,
    var isFav: Boolean,
    var isCapturado: Boolean,
   // var imagen: String
)

data class GetPokemoDetalleDto(
    var id: Long?,
    var nombre: String,
    var idPokedex: String,
    var estrellas: Int?,
    var ataqueRapido: String?,
    var ataqueCargado: String?,
    var pC: Int?,
    //var imagen: String?,
    var generacion: String?,
    var primerTipo: String,
    var segundoTipo: String?,
    var fav: Boolean
)

data class GetPokemonEquipoDto(
    var id: Long?,
    //var imagen: String?,
    var pC: Int?
)

/*fun Pokemon.toGetPokemonEquipo(): GetPokemonEquipoDto {

    lateinit var imagenPokemon: ImagenPokemon
    var url: String = "http://10.0.2.2:9000/files/"

    return GetPokemonEquipoDto(id, "${url}${imagenPokemon.dataId}",pC)
}*/

fun Pokemon.toGetPokemonDto(usuario: Usuario?): GetPokemonPokedexDto{

    var favorito = false
    var capturado = false

    if (usuario != null){
        for(pokemon in usuario.pokemonsFavs){
            if(pokemon.id == id){
                favorito = true
            }
        }
    }

    if (usuario != null){
        for( pokemon in usuario.pokemonsCapturados){
            if (pokemon.id == id){
                capturado =  true
            }
        }
    }


    /*lateinit var imagenPokemon: ImagenPokemon
    var url: String = "http://10.0.2.2:9000/files/"*/

    return GetPokemonPokedexDto(
        id,idPokedex, nombre, favorito, capturado /*"${url}${imagenPokemon.dataId}"*/
    )
}

fun Pokemon.toGetPokemonDetalleDto(usuario: Usuario?): GetPokemoDetalleDto {
    var favorito = false

    if (usuario != null){
        for(pokemon in usuario.pokemonsFavs){
            if(pokemon.id == id){
                favorito = true
            }
        }
    }

    /*var url: String = "http://10.0.2.2:9000/files/"*/

    /*lateinit var imagenPokemon: ImagenPokemon*/

    /*if (imagen != null) {
        return GetPokemoDetalleDto(
            id,
            nombre,
            estrellas,
            ataqueRapido,
            ataqueCargado,
            pC,
            //"${url}${imagenPokemon.dataId}",
            generacion?.nombre,
            primerTipo.nombreTipo,
            segundoTipo?.nombreTipo,
            favorito
        )
    }else{*/
        return GetPokemoDetalleDto(
            id,
            nombre,
            idPokedex,
            estrellas,
            ataqueRapido,
            ataqueCargado,
            pC,
            //"",
            generacion?.nombre,
            primerTipo.nombreTipo,
            segundoTipo?.nombreTipo,
            favorito
        )
    //}
}

