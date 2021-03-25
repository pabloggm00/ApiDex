package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.*

/**
 * Esta clase es para crear una imagen
 *
 * @param dataId ID de la foto
 * @param 
 */

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