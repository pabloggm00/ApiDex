package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*

@Entity
class Pokemon(
    var nombre: String,

    var estrellas: Int,

    var ataqueRapido: String,

    var ataqueCargado: String,

    var pc: Int,

    @ManyToOne
    var generacion: Pokedex,

    @ManyToOne
    var primerTipo: Tipo,

    @ManyToOne
    var segundoTipo: Tipo? = null,

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "imagen_id", referencedColumnName = "id")
    var imagen: ImagenPokemon,

    @ManyToMany
    @JoinTable(name = "favorito",
        joinColumns = [JoinColumn(name="pokemon_id")],
        inverseJoinColumns = [JoinColumn(name="usuario_id")]
    )
    var usuarioFavs: MutableList<Usuario> = mutableListOf(),

    @Id @GeneratedValue
    val id: Long? = null

    ) {
}