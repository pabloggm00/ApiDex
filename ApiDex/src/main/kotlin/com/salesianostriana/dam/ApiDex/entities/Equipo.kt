package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
class Equipo(

    @get:NotBlank(message = "{equipo.nombre.blank}")
    var nombre: String,

    @get:NotBlank(message = "{equipo.liga.blank}")
    var liga: Liga,

    @ManyToOne
    var usuario: Usuario,

    @OneToMany(mappedBy = "equipo")
    var listaPokemon: MutableList<Pokemon>,

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