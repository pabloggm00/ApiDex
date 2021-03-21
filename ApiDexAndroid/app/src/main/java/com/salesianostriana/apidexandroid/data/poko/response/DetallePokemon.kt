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
        val generacion: Pokedex,
        val primerTipo: Tipo,
        val segundoTipo: Tipo,
        val id: Long
)
