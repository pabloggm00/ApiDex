package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*

@Entity
class Usuario(
    var email: String,
    var username: String,
    var pass: String,
    var avatar: String,


    @ManyToMany
    @JoinTable(name = "favorito",
        joinColumns = [JoinColumn(name="usuario_id")],
        inverseJoinColumns = [JoinColumn(name="pokemon_id")]
    )
    var pokemonsFavs: MutableList<Pokemon> = mutableListOf(),


    @Id @GeneratedValue
    val id: Long? = null
) {
}