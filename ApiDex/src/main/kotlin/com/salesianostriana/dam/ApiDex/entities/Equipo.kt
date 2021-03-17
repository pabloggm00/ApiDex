package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*

@Entity
class Equipo(

    var nombre: String,

    var totalPC: Int,

    @ManyToOne
    var usuario: Usuario,

    @ManyToOne
    var liga: Liga,

    @OneToMany(mappedBy = "equipo")
    var listaPokemon: MutableList<Pokemon> = mutableListOf(),

    @Id @GeneratedValue
    val id: Long? = null
) {
}