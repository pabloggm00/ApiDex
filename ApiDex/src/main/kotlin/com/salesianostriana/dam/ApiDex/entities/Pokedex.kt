package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Pokedex(
    var generacion: Int,
    var nombre: String,

    @OneToMany(mappedBy = "generacion")
    var listaPokemon: MutableList<Pokemon> = mutableListOf(),

    @Id @GeneratedValue
    val id: Long? = null

) {
}