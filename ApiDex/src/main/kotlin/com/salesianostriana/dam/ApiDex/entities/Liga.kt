package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Liga(

    var nombre: String,

    @OneToMany(mappedBy = "liga")
    var listaEquipos: MutableList<Equipo> = mutableListOf(),

    @Id @GeneratedValue
    var id: Long? = null
) {
}