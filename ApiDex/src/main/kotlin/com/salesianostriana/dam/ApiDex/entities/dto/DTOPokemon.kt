package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.*
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.*


data class EditPokemonDto(
    @ApiModelProperty(
        example = "3",
        dataType= "Int",
        value = "Estrellas/Valoración"
    )
    @get:NotNull(message="{pokemon.estrellas.null}")
    @get:Min(1, message = "{pokemon.estrellas.min}")
    @get:Max(3, message = "{pokemon.estrellas.max}")
    var estrellas: Int?,

    @ApiModelProperty(
        example = "Látigo Cepa",
        dataType="String",
        value = "Ataque Rápido"
    )
    @get:NotBlank(message = "{pokemon.ataqueRapido.blank}")
    var ataqueRapido: String?,

    @ApiModelProperty(
        example = "Bomba Lodo",
        dataType="String",
        value = "Ataque Cargado"
    )
    @get:NotBlank(message = "{pokemon.ataqueCargado.blank}")
    var ataqueCargado: String?,

    @ApiModelProperty(
        example = "450",
        dataType="Int",
        value = "Puntos de combate"
    )
    @get:NotNull(message = "{pokemon.pC.null}")
    @get:Min(1, message = "{pokemon.pC.min}")
    var pC: Int?,

    @ApiModelProperty(
        example = "false",
        dataType="Boolean",
        value = "Pokemon Original"
    )
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
    var capturado: Boolean,
    var isOriginal: Boolean,
    var isUltimo: Boolean
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
            "#${idPokedex}",
            estrellas,
            ataqueRapido,
            ataqueCargado,
            pC,
            imagenPoke,
            generacion?.nombre,
            primerTipo.nombreTipo,
            segundoTipo?.nombreTipo,
            favorito,
            capturado,
            isOriginal,
            isUltimo
        )
    //}
}

