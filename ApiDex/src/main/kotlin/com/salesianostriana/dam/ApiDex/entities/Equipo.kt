package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*

@Entity
class Equipo(

    var nombre: String,

    var totalPC: Int,

    @OneToMany(mappedBy = "equipo")
    var listaPokemon: MutableList<Pokemon> = mutableListOf(),

    @ManyToOne
    var liga: Liga,

    @Id @GeneratedValue
    val id: Long? = null
) {
}