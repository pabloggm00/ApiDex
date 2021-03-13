package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Tipo(
    var nombreTipo: String,

    @Id @GeneratedValue
    val id: Long? = null
) {
}