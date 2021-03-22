package com.salesianostriana.apidexandroid.data.poko.response

data class DetallePokemon(
        val nombre: String,
        val idPokedex: String,
        val isFav: Boolean,
        val isCapturado: Boolean,
        val imagen: Imagen,
        val estrellas: Int,
        val pC: Int,
        val ataqueRapido: String,
        val ataqueCargado: String,
        val generacion: String,
        val primerTipo: String,
        val segundoTipo: String,
        val id: Long,
        val isOriginal: Boolean,
        val isUltimo: Boolean
)
