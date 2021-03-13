package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*

@Entity
class ImagenPokemon(
    var dataId: String?,
    var deleteHash: String?,

    @OneToOne(mappedBy = "pokemon")
    var pokemon: Pokemon,

    @Id @GeneratedValue
    val id: Long? = null

) {
}