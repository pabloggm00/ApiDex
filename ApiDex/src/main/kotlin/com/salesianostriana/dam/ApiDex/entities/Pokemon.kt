package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*
import javax.validation.constraints.*

/**
 * Esta clase es para crear un Pokémon
 * @author Pablo González González
 *
 * @param nombre Nombre del Pokémon
 * @param estrellas Valoración del Pokémon según su fuerza
 * @param ataqueRapido Ataque rápido del Pokémon
 * @param ataqueCargado Ataque cargado del Pokémon
 * @param pC Puntos de combate
 * @param idPokedex Número de la Pokédex
 * @param isUltimo Saber si es el último de su cadena evolutiva
 * @param isFav Saber si favorito o no
 * @param isCapturado Saber si esta capturado o no
 * @param isOriginal Saber si es el original de la Pokédex o no
 * @param equipo Equipo al que pertenece si es que pertenece a uno
 * @param generacion Generación a la que pertenece
 * @param primerTipo Primer tipo del Pokémon
 * @param segundoTipo Segundo tipo del Pokémon
 * @param imagen Imagen del Pokémon
 *
 */

@Entity
class Pokemon(
    var nombre: String,

    @get:NotNull(message="{pokemon.estrellas.null}")
    @get:Min(1, message = "{pokemon.estrellas.min}")
    @get:Max(3, message = "{pokemon.estrellas.max}")
    var estrellas: Int?,

    @get:NotBlank(message = "{pokemon.ataqueRapido.blank}")
    var ataqueRapido: String?,

    @get:NotBlank(message = "{pokemon.ataqueCargado.blank}")
    var ataqueCargado: String?,

    @get:NotNull(message = "{pokemon.pC.null}")
    @get:Min(1, message = "{pokemon.pC.min}")
    var pC: Int?,

    var idPokedex: String,

    var isUltimo: Boolean,

    var isFav: Boolean,

    var isCapturado: Boolean,

    //Para que no se pueda borrar ni editar el pokemon original de la base de datos
    var isOriginal: Boolean = false,


    @ManyToOne
    var equipo: Equipo? = null,

    @ManyToOne
    var generacion: Pokedex?,

    @ManyToOne
    var primerTipo: Tipo,

    @ManyToOne
    var segundoTipo: Tipo?,

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "imagen_id", referencedColumnName = "id")
    var imagen: Imagen? = null,

    @ManyToMany
    @JoinTable(name = "favoritos",
        joinColumns = [JoinColumn(name="pokemon_id")],
        inverseJoinColumns = [JoinColumn(name="usuario_id")]
    )
    var usuarioFavs: MutableList<Usuario> = mutableListOf(),

    @ManyToMany
    @JoinTable(name = "capturados",
        joinColumns = [JoinColumn(name="pokemon_id")],
        inverseJoinColumns = [JoinColumn(name="usuario_id")]
    )
    var usuarioCapturados: MutableList<Usuario> = mutableListOf(),

    @Id @GeneratedValue
    val id: Long? = null

    ) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Pokemon
        if (id != that.id) return false
        return true
    }


    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }

}