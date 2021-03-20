package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*

@Entity
class Imagen(
    var dataId: String?,
    var deleteHash: String?,

    @OneToOne(mappedBy = "imagen")
    var pokemon: Pokemon? = null,

    @OneToOne(mappedBy = "avatar")
    var usuario: Usuario? = null,

    @Id @GeneratedValue
    val id: Long? = null

) {
}