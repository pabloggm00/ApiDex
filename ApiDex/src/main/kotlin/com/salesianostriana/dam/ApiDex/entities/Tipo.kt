package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

/**
 * Esta clase sirve para crear un tipo
 *@author Pablo González González
 *
 * @param nombreTipo Nombre del tipo
 */

@Entity
class Tipo(
    var nombreTipo: String,

    @Id @GeneratedValue
    val id: Long? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Tipo
        if (id != that.id) return false
        return true
    }


    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}