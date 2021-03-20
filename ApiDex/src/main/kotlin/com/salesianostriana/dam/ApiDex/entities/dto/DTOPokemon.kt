package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.*


data class EditPokemonDto(
    var estrellas: Int,
    var ataqueRapido: String,
    var ataqueCargado: String,
    var pC: Int,
    var isOriginal: Boolean = false
)

data class GetPokemonPokedexDto(
    var id: Long?,
    var idPokedex: String,
    var nombre: String,
    var isFav: Boolean,
    var isCapturado: Boolean,
    var imagen: String
)

data class GetPokemonDetalleDto(
    var id: Long?,
    var nombre: String,
    var idPokedex: String,
    var estrellas: Int?,
    var ataqueRapido: String?,
    var ataqueCargado: String?,
    var pC: Int?,
    var imagen: GetImagenDetalleDto,
    var generacion: String?,
    var primerTipo: String,
    var segundoTipo: String?,
    var fav: Boolean,
    var capturado: Boolean
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

    var url: String = "http://10.0.2.2:9000/files/"


    return GetPokemonPokedexDto(
        id,"#${idPokedex}", nombre, favorito, capturado, "${url}${imagen!!.dataId}"
    )
}

fun Pokemon.toGetPokemonDetalleDto(usuario: Usuario?): GetPokemonDetalleDto {
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

    var url: String = "http://10.0.2.2:9000/files/"

    var imagenPoke: GetImagenDetalleDto = GetImagenDetalleDto(imagen!!.id, "${url}${imagen!!.dataId}", imagen!!.deleteHash)


   /* if (imagen != null) {
        return GetPokemonDetalleDto(
            id,
            nombre,
            idPokedex,
            estrellas,
            ataqueRapido,
            ataqueCargado,
            pC,
            imagenPoke,
            generacion?.nombre,
            primerTipo.nombreTipo,
            segundoTipo?.nombreTipo,
            favorito,
            capturado
        )
    }else{*/
        return GetPokemonDetalleDto(
            id,
            nombre,
            idPokedex,
            estrellas,
            ataqueRapido,
            ataqueCargado,
            pC,
            imagenPoke,
            generacion?.nombre,
            primerTipo.nombreTipo,
            segundoTipo?.nombreTipo,
            favorito,
            capturado
        )
    //}
}

