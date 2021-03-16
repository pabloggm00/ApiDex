package com.salesianostriana.dam.ApiDex.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Tipo(
    var nombreTipo: String,

    /*@OneToMany(mappedBy = "primerTipo")
    var pokemonUno: Pokemon,*/

    /*@OneToMany(mappedBy = "segundoTipo")
    var pokemonDos: Pokemon?,*/

    @Id @GeneratedValue
    val id: Long? = null
) {
}