package com.salesianostriana.apidexandroid.data.poko.response

data class Pokedex(
        val generacion: Int,
        val nombre: String,
        val id: Long,
        val listaPokemon: List<Pokemon>
)
