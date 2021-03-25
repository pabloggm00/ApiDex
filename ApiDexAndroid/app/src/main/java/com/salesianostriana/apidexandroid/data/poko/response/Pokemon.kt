package com.salesianostriana.apidexandroid.data.poko.response

data class Pokemon (
    val nombre: String,
    val estrellas: Int,
    val ataqueRapido: String,
    val ataqueCargado: String,
    val pC: Int,
    val imagen: String,
    val primerTipo: Int,
    val segundoTipo: Int,
    val idPokedex: String,
    val isUltimo: Boolean,
    val isCapturado: Boolean,
    val isOriginal: Boolean,
    val isFav: Boolean,
    val id: Long,
    val generacion: String

)