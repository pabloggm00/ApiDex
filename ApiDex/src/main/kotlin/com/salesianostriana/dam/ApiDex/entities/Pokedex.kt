package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

/**
 * Esta clase sirve para crear una Pokédex
 * @author Pablo González González
 *
 * @param generacion Número de la generación
 * @param nombre Región
 * @param listaPokemon Listado de Pokémon
 */

@Entity
class Pokedex(
    var generacion: Int,
    var nombre: String,

    @OneToMany(mappedBy = "generacion")
    var listaPokemon: MutableList<Pokemon> = mutableListOf(),

    @Id @GeneratedValue
    val id: Long? = null

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Pokedex
        if (id != that.id) return false
        return true
    }


    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}