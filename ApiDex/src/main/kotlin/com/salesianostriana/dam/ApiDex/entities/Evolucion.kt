package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Evolucion(

    var estadoEvolucionActual: Int,

    var estadoEvolucionMax: Int,

    /*@OneToOne(mappedBy = "evolucion")
    var pokemon: Pokemon,*/

    @Id @GeneratedValue
    val id: Long? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Evolucion
        if (id != that.id) return false
        return true
    }


    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}