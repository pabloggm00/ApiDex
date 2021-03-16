package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Evolucion(

    var estadoEvolucionActual: Int,

    var estadoEvolucionMax: Int,

    @OneToOne(mappedBy = "evolucion")
    var pokemon: Pokemon,

    @Id @GeneratedValue
    val id: Long? = null
) {
}