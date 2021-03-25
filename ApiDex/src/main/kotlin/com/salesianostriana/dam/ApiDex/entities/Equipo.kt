package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 *
 * Esta clase es para crear un Equipo
 *
 * @param nombre Nombre del equipo
 * @param liga Liga a la que pertenece
 * @param usuario Usuario del que pertenece al equipo
 * @param listaPokemon Listado de Pokémon del equipo
 *
 */

@Entity
class Equipo(

    @get:NotBlank(message = "{equipo.nombre.blank}")
    var nombre: String,

    var liga: Liga,

    @ManyToOne
    var usuario: Usuario,

    @OneToMany(mappedBy = "equipo")
    var listaPokemon: MutableList<Pokemon> = mutableListOf(),

    @Id @GeneratedValue
    var id: Long? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Equipo
        if (id != that.id) return false
        return true
    }


    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}