package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.*
import javax.validation.constraints.*


data class EditPokemonDto(
    @get:NotNull(message="{pokemon.estrellas.null}")
    @get:Min(1, message = "{pokemon.estrellas.min}")
    @get:Max(3, message = "{pokemon.estrellas.max}")
    var estrellas: Int?,

    @get:NotBlank(message = "{pokemon.ataqueRapido.blank}")
    @get:Size(message = "{pokemon.ataqueRapido.size}", min = 2)
    var ataqueRapido: String?,

    @get:NotBlank(message = "{pokemon.ataqueCargado.blank}")
    @get:Size(message = "{pokemon.ataqueCargado.size}", min = 2)
    var ataqueCargado: String?,

    @get:NotNull(message = "{pokemon.pC.null}")
    @get:Min(1, message = "{pokemon.pC.min}")
    var pC: Int?,
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
    var imagen: String?,
    var pC: Int?
)

fun Pokemon.toGetPokemonEquipo(): GetPokemonEquipoDto {

    val url: String = "http://10.0.2.2:9000/files/"

    return GetPokemonEquipoDto(id, "${url}${imagen!!.dataId}",pC)
}

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

    val url: String = "http://10.0.2.2:9000/files/"


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

