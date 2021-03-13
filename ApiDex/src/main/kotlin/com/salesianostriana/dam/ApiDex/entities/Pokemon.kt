package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*

@Entity
class Pokemon(
    var nombre: String,

    @ManyToOne
    var pokedex: Pokedex,

    @ManyToOne
    var primerTipo: Tipo,

    @ManyToOne
    var segundoTipo: Tipo,

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "imagen_id", referencedColumnName = "id")
    var imagen: ImagenPokemon,

    @ManyToMany
    @JoinTable(name = "favorito",
        joinColumns = [JoinColumn(name="pokemon_id")],
        inverseJoinColumns = [JoinColumn(name="usuario_id")]
    )
    var usuarioFavs: MutableList<Usuario> = mutableListOf(),

    @ManyToMany
    @JoinTable(name = "capturados",
        joinColumns = [JoinColumn(name="pokemon_id")],
        inverseJoinColumns = [JoinColumn(name="usuario_id")]
    )
    var usuarioCapturados: MutableList<Usuario> = mutableListOf(),


    var vulnerableTo: MutableList<Tipo>

) {
}