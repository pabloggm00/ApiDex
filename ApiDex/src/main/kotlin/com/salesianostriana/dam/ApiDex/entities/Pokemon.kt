package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*

@Entity
class Pokemon(
    var nombre: String,

    var estrellas: Int?,

    var ataqueRapido: String?,

    var ataqueCargado: String?,

    var pC: Int?,

    var idPokedex: String,

    var isUltimo: Boolean,

    var isCapturado: Boolean,

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name="evolucion_id", referencedColumnName = "id")
    var evolucion: Evolucion?= null,

    @ManyToOne
    var equipo: Equipo?=null,

    @ManyToOne
    var generacion: Pokedex?,

    @ManyToOne
    var primerTipo: Tipo,

    @ManyToOne
    var segundoTipo: Tipo?,

    //@OneToOne(cascade = arrayOf(CascadeType.ALL))
    //@JoinColumn(name = "imagen_id", referencedColumnName = "id")
    //var imagen: ImagenPokemon? = null,

    @ManyToMany
    @JoinTable(name = "favoritos",
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

    @Id @GeneratedValue
    val id: Long? = null

    ) {
}